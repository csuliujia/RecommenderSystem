package com.dtdream.DtRecommender.cfgMgr.impl.biz.watch;

import com.dtdream.DtRecommender.cfgMgr.entity.biz.RBiz;
import com.dtdream.DtRecommender.cfgMgr.utils.PathHelper;
import com.dtdream.DtRecommender.cfgMgr.zk.BizItem;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.dtdream.DtRecommender.cfgMgr.zk.ZNodePath.PATH_BIZCFG_ROOT;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.*;
import static java.lang.Thread.sleep;
import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

/**
 * Created by handou on 10/14/16.
 *
 * 关注四件事情：
 * 1、业务实例节点的创建
 * 2、业务实例节点 上数据的更新
 * 3、业务实例节点 下的子节点 的watch 设置
 * 4、业务实例  的内存数据的维护
 *
 *
 */
public class BizWatcher implements Watcher {
    private final static Logger log = LoggerFactory.getLogger(BizWatcher.class);
    private ZooKeeper zk;
    private Map<String, RBiz> rBizs_data;

    public BizWatcher(ZooKeeper zk, Map<String, RBiz> rBizs_data) {
        this.zk = zk;
        this.rBizs_data = rBizs_data;
    }

    private void createRBiz(String key, BizItem data) {
        rBizs_data.put(key, new RBiz(data));
    }

    private void deleteRBiz(String key) {
        rBizs_data.remove(key);
    }

    /* 只关注Biz 节点的data数据，不需要关注其 子节点  */
    private void updateRBiz(String key, BizItem newData) {
        rBizs_data.get(key).setBiz_data(newData);
    }

    /* 这里仅 更新业务的数据， 场景数据不需要处理，由其下挂接的场景子节点进行处理 */
    private void updateRData(String path) {
        String[] p = path.split("/");

        String biz_code = p[p.length - 1];
        BizItem data = getData(zk, path, this, BizItem.class);
        updateRBiz(biz_code, data);

        /* 触发下次的数据更新 */
        exists(zk, path, this);
    }

    /* 更新本地内存数据, Biz节点的 Watcher 处理接口 */
    private void procChildChanged(String father, String child) {
        String path = PathHelper.getPath(father, child);
        String biz_code = child;

        boolean local = rBizs_data.containsKey(biz_code);
        boolean remote = exists(zk, path, this);

        /* 删除child */
        if (local && !remote) {
            deleteRBiz(biz_code);

            return;
        }

        /* 创建child */
        if (!local && remote) {

            /* 设置关注 child 节点数据变化 */
            BizItem zdata = getData(zk, path, this, BizItem.class);
            createRBiz(biz_code, zdata);

            /* 设置 关注其下子节点 （场景） 的 watch  */
            Watcher w = new SceneWatcher(zk, biz_code, rBizs_data.get(biz_code).getScnList());

            getChildren(zk, path, w);
        }

        /* 业务节点 数据更新的操作 直接在 NodeDataChanged 事件中处理 */
    }

    /* 更新 father 下所有子节点 的数据， 处理创建/删除 事件 */
    private void procChildrenChanged(String father)  {

        /* 继续关注该节点下 其他子节点的创建事件 */
        List<String> children = getChildren(zk, father, this);

        log.info("father {} child list: {}",  father, children);

        Set<String> all = new HashSet<String>();

        if (!children.isEmpty()) {
            all.addAll(children);
        }

        if (!rBizs_data.isEmpty()) {
            all.addAll(new ArrayList<String>(rBizs_data.keySet()));
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
            log.info("[BizWatcher] Current State: {}", state);

            return;
        }

        switch (eType) {
            case None:

                /* 从zookeeper拉 整棵树的数据，同步到本地r_data */
                batchRecover();
                break;

            case NodeChildrenChanged:

                /* 取出子节点，立即重新设置 path节点下子节点 的创建， 要快，慢了就没戏了 */
                List<String> list = getChildren(zk, path, this);
                boolean flag = exists(zk, path + "/car_code", null);

                log.info("[BizWatcher] event: {}, path: {}, child list: {} flag: {}", eType.toString(), path, list, flag);

                /* 响应处理 业务节点 的创建/删除 */
                procChildrenChanged(path);
                break;

            case NodeCreated:

                /* 业务的watch 只负责处理 根节点（PATH_BIZCFG_ROOT） 的创建 其下子节点（业务实例）的创建 都不要考虑 */
                List<String>  l = getChildren(zk, path, this);
                log.info("[BizWatcher] event: {}, path: {}, list: {}", eType.toString(), path, l);
                break;

            case NodeDataChanged:

                /* 更新本地内存数据r_data */
                log.info("[BizWatcher] event: {}, path: {}", eType.toString(), path);

                updateRData(event.getPath());
                break;

            default:

                log.info("[BizWatcher] Current type: {}", eType);
                break;

        }
    }

    /* 批备接口:从zooker中拉取数据恢复到全局的 rBizs_data , 只需要在模块的顶层做一次  */
    private void batchRecover() {

        /* 只能获取到一级子节点， 二级以上获取不到，需要递归遍历 */
        try {
            zk.getChildren(PATH_BIZCFG_ROOT, false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
