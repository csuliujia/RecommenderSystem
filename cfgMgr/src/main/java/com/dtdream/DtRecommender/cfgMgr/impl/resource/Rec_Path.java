package com.dtdream.DtRecommender.cfgMgr.impl.resource;

import com.dtdream.DtRecommender.cfgMgr.entity.resource.ObjectType;
import org.apache.zookeeper.*;

/**
 *
 */
public class Rec_Path {

    public static void initRootPath(ZooKeeper zooKeeper,Watcher watcher){
        try {
            if (zooKeeper.exists("/" + ObjectType.RESOURCECFG.getType(),watcher) == null) {
                zooKeeper.create("/" + ObjectType.RESOURCECFG.getType(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                zooKeeper.getChildren("/"+ObjectType.RESOURCECFG.getType(),watcher);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initOfflinePath(ZooKeeper zooKeeper,Watcher watcher){
        try {
            if (zooKeeper.exists("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.OFFLINE.getType(), watcher) == null) {
                zooKeeper.create("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.OFFLINE.getType(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initDBPath(ZooKeeper zooKeeper,Watcher watcher){
        try{
            if (zooKeeper.exists("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.DB.getType(),watcher) == null) {
                zooKeeper.create("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.DB.getType(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initKVStorePath(ZooKeeper zooKeeper,Watcher watcher){
        try {
            if (zooKeeper.exists("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.KVSTORE.getType(),watcher) == null) {
                zooKeeper.create("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.KVSTORE.getType(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initMQPath(ZooKeeper zooKeeper,Watcher watcher){
        try {
            if (zooKeeper.exists("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.MQ.getType(),watcher) == null) {
                zooKeeper.create("/" + ObjectType.RESOURCECFG.getType() + "/" + ObjectType.MQ.getType(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getOfflinePath(String name){ return "/"+ ObjectType.RESOURCECFG.getType()+"/"+ObjectType.OFFLINE.getType()+"/" + name; }

    public static String getDBPath(String name) { return "/"+ ObjectType.RESOURCECFG.getType()+"/"+ObjectType.DB.getType()+"/" + name; }

    public static String getKVStorePath(String name) { return "/"+ ObjectType.RESOURCECFG.getType()+"/"+ObjectType.KVSTORE.getType()+"/"+ name; }

    public static String getMQPath(String name) { return "/"+ ObjectType.RESOURCECFG.getType()+"/"+ObjectType.MQ.getType()+"/"+ name; }

}
