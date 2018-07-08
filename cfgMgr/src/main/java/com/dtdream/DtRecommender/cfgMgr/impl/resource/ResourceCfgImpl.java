package com.dtdream.DtRecommender.cfgMgr.impl.resource;

import com.dtdream.DtRecommender.cfgMgr.ResourceCfg;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.ObjectType;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.DBRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.KVStoreRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.MQRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.OfflineRec;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import static com.dtdream.DtRecommender.cfgMgr.impl.resource.Rec_Path.*;
import static com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper.toBytes;
import static org.apache.zookeeper.Watcher.Event.KeeperState.Disconnected;
import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

/**
 *
 */
public class ResourceCfgImpl implements ResourceCfg, Watcher {
    private ZooKeeper zooKeeper;
    private final static Logger log = LoggerFactory.getLogger(ResourceCfgImpl.class);

    /* 静态全局变量rec_data和rec_list，用来存储全部配置数据 */
    public static HashMap<String, Object> rec_data ;
    public static HashMap<String,List<Object>> rec_list ;

    public ResourceCfgImpl(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;

        rec_data = new HashMap<String, Object>();
        rec_list = new HashMap<String, List<Object>>();

        /* 判断RootPath是否存在，如不存在则创建 */
        initRootPath(zooKeeper, this);
        /* 初始化时批量更新 */
        batchRecover();
    }

    public void createOfflineRec(OfflineRec olRec) {
        String path = getOfflinePath(olRec.getName());
        byte[] data = toBytes(olRec);

        /* 判断Offline的根节点是否存在，如不存在则创建*/
        initOfflinePath(zooKeeper, this);
        createRec(path, data);

    }

    public void createKVStoreRec(KVStoreRec kvsRec) {
        String path = getKVStorePath(kvsRec.getName());
        byte[] data = toBytes(kvsRec);

        initKVStorePath(zooKeeper, this);
        createRec(path, data);

    }

    public void createDBRec(DBRec dbRec) {
        String path = getDBPath(dbRec.getName());
        byte[] data = toBytes(dbRec);

        initDBPath(zooKeeper, this);
        createRec(path, data);
    }

    public void createMQRec(MQRec mqRec) {
        String path = getMQPath(mqRec.getName());
        byte[] data = toBytes(mqRec);

        initMQPath(zooKeeper,this);
        createRec(path, data);
    }

    public void updateOfflineRec(OfflineRec olRec) {
        String path = getOfflinePath(olRec.getName());
        byte[] data = toBytes(olRec);

        updateRec(path, data);
    }

    public void updateKVStoreRec(KVStoreRec kvsRec) {
        String path = getKVStorePath(kvsRec.getName());
        byte[] data = toBytes(kvsRec);

        updateRec(path,data);
    }

    public void updateDBRec(DBRec dbRec) {
        String path = getDBPath(dbRec.getName());
        byte[] data = toBytes(dbRec);

        updateRec(path,data);
    }

    public void updateMQRec(MQRec mqRec) {
        String path = getMQPath(mqRec.getName());
        byte[] data = toBytes(mqRec);

        updateRec(path, data);
    }

    public void deleteOfflineRec(String name) {
        String path = getOfflinePath(name);

        deleteRec(path);
    }

    public void deleteKVStoreRec(String name) {
        String path = getKVStorePath(name);

        deleteRec(path);

    }

    public void deleteDBRec(String name) {
        String path = getDBPath(name);

        deleteRec(path);
    }

    public void deleteMQRec(String name) {
        String path = getMQPath(name);

        deleteRec(path);
    }

    public OfflineRec getOfflineRec(String name) {
        OfflineRec offlineRec = (OfflineRec) Rec_Data.getData(name);
        return offlineRec;
    }

    public KVStoreRec getKVStoreRec(String name) {
        KVStoreRec kvStoreRec = (KVStoreRec) Rec_Data.getData(name);
        return kvStoreRec;
    }

    public DBRec getDBRec(String name) {
        DBRec dbRec = (DBRec) Rec_Data.getData(name);
        return dbRec;
    }

    public MQRec getMQRec(String name) {
        MQRec mqRec = (MQRec) Rec_Data.getData(name);
        return mqRec;
    }

    public List<OfflineRec> getOfflineRecList(String type) {
        List<OfflineRec> offlineList = Rec_Data.getOfflineList(type);
        return offlineList;
    }

    public List<KVStoreRec> getKVStoreRecList(String type) {
        List<KVStoreRec> kvStoreList = Rec_Data.getKVStoreList(type);
        return kvStoreList;
    }

    public List<DBRec> getDBRecList(String type) {
        List<DBRec> dbRecList = Rec_Data.getDBList(type);
        return dbRecList;
    }

    public List<MQRec> getMQRecList(String type) {
        List<MQRec> mqRecList = Rec_Data.getMQRecList(type);
        return mqRecList;
    }

    /* 考虑：client与Server在不同状态下，对收到的不同事件类型进行处理 */
    public void process(WatchedEvent event) {
        String path = event.getPath();
        Event.KeeperState state = event.getState();
        Event.EventType eType = event.getType();

        if (state == SyncConnected) {
            switch (eType) {

                case NodeDataChanged:       /* 所关注的节点的内容有更新 */

                    dataUpdate(path);
                    break;

                case NodeChildrenChanged:   /* 所关注的节点的子节点有变化 */

                    childrenDataUpdate();
                    break;

                default:
                    log.info("invalid event type: {}", eType);
                    break;
            }


        } else if (state == Disconnected) {


        } else {
            log.info("other state: {}", state);
        }

    }

    /* 在zookeeper中创建资源 */
    private void createRec(String path,byte[] data){

        try {
            zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.exists(path, this);   /* 监控该节点内容变化 */
            zooKeeper.setData(path,data,-1);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* 在zookeeper中更新资源 */
    private void updateRec(String path,byte[] data){

        try {
            zooKeeper.exists(path, this);
            zooKeeper.setData(path, data, -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* 在zookeeper中删除资源 */
    private void deleteRec(String path){

        try {
            zooKeeper.exists(path, this);
            zooKeeper.delete(path, -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* zookeeper中数据批量更新到全局的rec_data和rec_list */
    private void batchRecover() {
        try {
            List<String> childrenList = zooKeeper.getChildren("/"+ ObjectType.RESOURCECFG.getType(),true);
            Iterator<String> it = childrenList.iterator();

            while (it.hasNext()) {
                String objectType = it.next();
                List<String> nameList = zooKeeper.getChildren("/"+ ObjectType.RESOURCECFG.getType()+"/"+objectType,true);
                Iterator<String> its = nameList.iterator();
                while(its.hasNext()){
                    Rec_Data.updateData(objectType, its.next(), zooKeeper, this);
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* zookeeper中数据单个更新到全局的rec_data和rec_list */
    private void dataUpdate(String path) {
        String[] pps = path.split("/");
        String resourceType = pps[pps.length - 2];
        String resourceName = pps[pps.length - 1];

        Rec_Data.updateData(resourceType, resourceName, zooKeeper,this);
    }

    /* 某节点的子节点变化后，目前拉取数据恢复到全局的rec_data的方式为拉取全部子节点
       但实际应只拉取变动的子节点(最好) */
    private void childrenDataUpdate() {
        try {
            List<String> childrenList = zooKeeper.getChildren("/" + ObjectType.RESOURCECFG.getType(), true);
            Iterator<String> it = childrenList.iterator();

            while (it.hasNext()) {
                List<String> nameList = zooKeeper.getChildren("/" + ObjectType.RESOURCECFG.getType() + "/" + it.next(), true);
                Iterator<String> its = nameList.iterator();
                while (its.hasNext()) {
                    Rec_Data.updateData(it.next(), its.next(), zooKeeper, this);
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

