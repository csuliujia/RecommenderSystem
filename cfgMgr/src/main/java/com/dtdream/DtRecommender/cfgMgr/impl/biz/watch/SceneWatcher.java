package com.dtdream.DtRecommender.cfgMgr.impl.biz.watch;

import com.dtdream.DtRecommender.cfgMgr.entity.biz.RScene;
import com.dtdream.DtRecommender.cfgMgr.utils.PathHelper;
import com.dtdream.DtRecommender.cfgMgr.zk.ScnItem;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.dtdream.DtRecommender.cfgMgr.zk.ZNodePath.PATH_BIZCFG_ROOT;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.exists;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.getChildren;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.getData;
import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

/**
 * Created by handou on 10/14/16.
 *
 * 场景节点的 watch 处理类，主要是用户处理场景 运行数据的处理， 被 Busines 实现接口类调用
 *
 */
public class SceneWatcher implements Watcher {
    private final static Logger log = LoggerFactory.getLogger(SceneWatcher.class);
    private ZooKeeper zk;
    private String biz_code;

    private Map<String, RScene> rScns_data;

    public SceneWatcher(ZooKeeper zk, String biz, Map<String, RScene> rScns_data) {
        this.zk = zk;
        this.biz_code = biz;

        this.rScns_data = rScns_data;
    }

    private void createRScn(String key, ScnItem data) {
        rScns_data.put(key, new RScene(data));
    }

    private void deleteRScn(String key) {
        rScns_data.remove(key);
    }

    /* 只关注Scn 节点的data数据，不需要关注其 子节点  */
    private void updateRScn(String key, ScnItem newData) {
        rScns_data.get(key).setScn_data(newData);
    }

    /* 这里仅 更新场景的数据， 策略数据不需要处理，由其下挂接的策略子节点进行处理 */
    private void updateRData(String path) {
        String[] p = path.split("/");

        String biz_code = p[p.length - 1];
        ScnItem data = getData(zk, path, this, ScnItem.class);
        updateRScn(biz_code, data);

        /* 触发下次的数据更新 */
        exists(zk, path, this);
    }

    /* 更新本地内存数据, Biz节点的 Watcher 处理接口 */
    private void procChildChanged(String father, String child) {
        String path = PathHelper.getPath(father, child);
        String scn_code = child;

        boolean local = rScns_data.containsKey(scn_code);
        boolean remote = exists(zk, path, null);

        /* 删除child */
        if (local && !remote) {
            deleteRScn(scn_code);

            return;
        }

        /* 创建child */
        if (!local && remote) {

            ScnItem zdata = getData(zk, path, this, ScnItem.class);
            createRScn(scn_code, zdata);

            /* 设置关注 child 节点 (策略节点)  数据变化 */
            getData(zk, path, this);

            /* 设置 关注其下子节点 （场景） 的 watch  */
            getChildren(zk, path, new PolicyWatcher(zk, biz_code, scn_code, rScns_data.get(scn_code).getPolicyItems()));
        }

        /* 业务节点 数据更新的操作 直接在 NodeDataChanged 事件中处理 */
    }

    /* 更新 业务节点 下所有子节点 的数据， 处理创建/删除 事件 */
    private void procChildrenChanged(String father)  {
        List<String> children = getChildren(zk, father, this);

        Set<String> all = new HashSet<String>();

        if (!children.isEmpty()){
            all.addAll(children);
        }

        if (!rScns_data.keySet().isEmpty()) {
            all.addAll(new ArrayList<String>(rScns_data.keySet()));
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
                log.info("[SceneWatcher] event: {}, path: {}", eType.toString(), path);


                procChildrenChanged(path);
                break;

            case NodeCreated:

                /*if (path.equals(PathHelper.getPath(PATH_BIZCFG_ROOT, this.biz_code))) {*/
                log.info("[SceneWatcher] event: {}, path: {}", eType.toString(), path);


                    getChildren(zk, path, this);
             /*   }*/
                break;

            case NodeDataChanged:

                /* 更新本地内存数据r_data */
                log.info("[SceneWatcher] event: {}, path: {}", eType.toString(), path);

                updateRData(event.getPath());
                break;

            default:

                log.info("[SceneWatcher] Current type: {}", eType);
                break;
        }
    }
}
