package com.dtdream.DtRecommender.cfgMgr.impl.resource;

import com.dtdream.DtRecommender.cfgMgr.entity.resource.ObjectType;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.DBRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.KVStoreRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.MQRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.OfflineRec;
import com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Rec_Data {
    private final static Logger log = LoggerFactory.getLogger(Rec_Data.class);

    public static void updateData(String objectType,String objectName,ZooKeeper zooKeeper,Watcher watcher){
        List<Object> objectList = new ArrayList<Object>();

        try{
            switch (ObjectType.valueOf(objectType)){
                case OFFLINE:
                    OfflineRec olRec = BytesHelper.fromBytes(zooKeeper.getData("/"+ ObjectType.RESOURCECFG.getType()+
                            "/"+objectType+"/"+objectName,watcher, null), OfflineRec.class);
                    ResourceCfgImpl.rec_data.put(olRec.getName(), olRec);
                    objectList.add(olRec);
                    ResourceCfgImpl.rec_list.put(objectType, objectList);
                    break;

                case KVSTORE:
                    KVStoreRec kvsRec = BytesHelper.fromBytes(zooKeeper.getData("/"+ ObjectType.RESOURCECFG.getType()+
                            "/"+objectType+"/"+objectName, watcher, null), KVStoreRec.class);
                    ResourceCfgImpl.rec_data.put(kvsRec.getName(), kvsRec);
                    objectList.add(kvsRec);
                    ResourceCfgImpl.rec_list.put(objectType, objectList);
                    break;

                case DB:
                    DBRec dbRec = BytesHelper.fromBytes(zooKeeper.getData("/"+ ObjectType.RESOURCECFG.getType()+
                            "/"+objectType+"/"+objectName, watcher, null), DBRec.class);
                    ResourceCfgImpl.rec_data.put(dbRec.getName(), dbRec);
                    objectList.add(dbRec);
                    ResourceCfgImpl.rec_list.put(objectType, objectList);
                    break;

                case MQ:
                    MQRec mqRec = BytesHelper.fromBytes(zooKeeper.getData("/"+ ObjectType.RESOURCECFG.getType()+
                            "/"+objectType+"/"+objectName, watcher, null), MQRec.class);
                    ResourceCfgImpl.rec_data.put(mqRec.getName(), mqRec);
                    objectList.add(mqRec);
                    ResourceCfgImpl.rec_list.put(objectType,objectList);
                    break;

                default:
                    log.info("invalid object type: {}", objectType);
                    break;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
}

    public static Object getData(String name){
        Object o = ResourceCfgImpl.rec_data.get(name);

        return o;
    }

    public static List<OfflineRec> getOfflineList(String type){
        List<OfflineRec> offlineList = new ArrayList<OfflineRec>();
        for(Object o : ResourceCfgImpl.rec_list.get(type)){
            offlineList.add((OfflineRec)o);
        }
        return offlineList;
    }

    public static List<KVStoreRec> getKVStoreList(String type){
        List<KVStoreRec> KVStoreList =  new ArrayList<KVStoreRec>();
        for(Object o : ResourceCfgImpl.rec_list.get(type)){
            KVStoreList.add((KVStoreRec)o);
        }
        return KVStoreList;
    }

    public static List<DBRec> getDBList(String type){
        List<DBRec> DBList =  new ArrayList<DBRec>();
        for(Object o : ResourceCfgImpl.rec_list.get(type)){
            DBList.add((DBRec)o);
        }
        return DBList;
    }

    public static List<MQRec> getMQRecList(String type){
        List<MQRec> MQList =  new ArrayList<MQRec>();
        for(Object o : ResourceCfgImpl.rec_list.get(type)){
            MQList.add((MQRec)o);
        }
        return MQList;
    }

}
