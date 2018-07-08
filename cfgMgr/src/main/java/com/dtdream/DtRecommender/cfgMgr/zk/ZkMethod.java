package com.dtdream.DtRecommender.cfgMgr.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

import static com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper.fromBytes;

/**
 * Created by handou on 10/14/16.
 * 对Zookeeper的基本操作封装
 *
 */
public class ZkMethod {

    public static void createRootNode(ZooKeeper zk, String root, Watcher w) {
        try {
            /* 关注根节点的创建事件 */
            zk.exists(root, w);
            zk.create(root, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void createNormalNode(ZooKeeper zk, String path, byte[] data) {
        try {
            zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getChildren(ZooKeeper zk, String father, Watcher w) {
        List<String> children = null;
        try {
            children = zk.getChildren(father, w);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return children;
    }

    public static <T> T getData(ZooKeeper zk, String path, Watcher w, Class<T> mapClazz) {
        T zdata = null;

        try {

            byte[] data = zk.getData(path, w, null);
            zdata = fromBytes(data, mapClazz);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return zdata;
    }

    public static byte[] getData(ZooKeeper zk, String path, Watcher w) {
        byte[] data = null;

        try {

            data = zk.getData(path, w, null);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static boolean exists(ZooKeeper zk, String path, Watcher w) {
        Stat r = null;

        try {
            r = zk.exists(path, w);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return r != null;
    }

    public static void updateData(ZooKeeper zk, String path, byte[] data, Watcher w) {
        try {
            /* 设置path的watcher */
            zk.exists(path, w);

            /* 给path 添加数据 */
            zk.setData(path, data, -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void delete(ZooKeeper zk, String path) {
        try {
            zk.delete(path, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGroup(ZooKeeper zk, String path) {

        List<String> children = ZkMethod.getChildren(zk, path, null);
        for(String child: children){
            ZkMethod.delete(zk, path + "/" +child);
        }

        ZkMethod.delete(zk, path);
    }

}
