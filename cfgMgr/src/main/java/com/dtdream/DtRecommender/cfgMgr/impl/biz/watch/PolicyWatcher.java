package com.dtdream.DtRecommender.cfgMgr.impl.biz.watch;

import com.dtdream.DtRecommender.cfgMgr.utils.PathHelper;
import com.dtdream.DtRecommender.cfgMgr.zk.PolicyItem;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.dtdream.DtRecommender.cfgMgr.zk.ZNodePath.PATH_BIZCFG_ROOT;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.*;
import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

/**
 * Created by handou on 10/14/16.
 * 处理策略 配置节点的 watch
 *
 */
public class PolicyWatcher implements Watcher {
    private final static Logger log = LoggerFactory.getLogger(PolicyWatcher.class);
    private ZooKeeper zk;
    private String biz_code;
    private String scn_code;
    private Map<String, PolicyItem> rPolicies_data;

    public PolicyWatcher(ZooKeeper zk, String biz_code, String scn_code, Map<String, PolicyItem> rPolicies_data) {
        this.zk = zk;
        this.biz_code = biz_code;
        this.scn_code = scn_code;
        this.rPolicies_data = rPolicies_data;
    }

    /* 策略 叶子节点的更新 简单覆盖旧的的value */
    private void updatePolicy(String key, PolicyItem newData) {
        rPolicies_data.put(key, newData);
    }
    private void createRPolicy(String key, PolicyItem data) {
        rPolicies_data.put(key, data);
    }

    private void deleteRPolicy(String key) {
        rPolicies_data.remove(key);
    }

    /* 这里仅 更新策略的数据， 场景数据不需要处理，由其下挂接的场景子节点进行处理 */
    private void updateRData(String path) {
        String[] p = path.split("/");

        String policy_name = p[p.length - 1];
        PolicyItem data = getData(zk, path, this, PolicyItem.class);

        updatePolicy(policy_name, data);

        /* 触发下次的数据更新 */
        exists(zk, path, this);
    }

    /* 更新本地内存数据, Biz节点的 Watcher 处理接口 */
    private void procChildChanged(String father, String child) {
        String path = PathHelper.getPath(father, child);
        String polcy_name = child;

        boolean local = rPolicies_data.containsKey(polcy_name);
        boolean remote = exists(zk, path, null);

        /* 删除child */
        if (local && !remote) {
            deleteRPolicy(polcy_name);

            return;
        }

        /* 创建child */
        if (!local && remote) {

            PolicyItem zdata = getData(zk, path, this, PolicyItem.class);
            createRPolicy(polcy_name, zdata);

            /* 设置关注 child 节点数据变化 */
            getData(zk, path, this);

            /* policy已经是叶子节点，不存在 其下的子节点 需要观察了 */
        }



        /* 业务节点 数据更新的操作 直接在 NodeDataChanged 事件中处理 */
    }

    /* 更新 场景节点 下所有子节点 的数据， 处理创建/删除 事件 */
    private void procChildrenChanged(String father)  {
        List<String> children = getChildren(zk, father, this);

        Set<String> all = new HashSet<String>();

        assert (!children.isEmpty());
        all.addAll(children);

        if (!rPolicies_data.keySet().isEmpty()){
            all.addAll(new ArrayList<String>(rPolicies_data.keySet()));
        }

        for (String e : all) {
            procChildChanged(father, e);
        }
    }

    /* 考虑：client与Server在不同状态下，对收到的不同事件类型进行处理 */
    public void process(WatchedEvent event) {
        Event.KeeperState state = event.getState();
        Event.EventType eType = event.getType();
        String path = event.getPath();

        if (state != SyncConnected) {
            log.info("[SceneWatcher] Current State: {}", state);

            return;
        }

        switch (eType) {
            case None:
                break;

            case NodeChildrenChanged:

                /* 响应处理 业务节点 的创建/删除 */

                log.info("[PolicyWatcher] event: {}, path: {}", eType.toString(), path);

                procChildrenChanged(path);
                break;

            case NodeCreated:

                if (path.equals(PathHelper.getPath(PATH_BIZCFG_ROOT, biz_code, scn_code))) {
                    log.info("[PolicyWatcher] event: {}, path: {}", eType.toString(), path);


                    getChildren(zk, path, this);
                }
                break;

            case NodeDataChanged:

                /* 更新本地内存数据r_data */

                log.info("[PolicyWatcher] event: {}, path: {}", eType.toString(), path);
                updateRData(path);
                break;

            default:

                log.info("[SceneWatcher] Current type: {}", eType);
                break;
        }
    }
}
