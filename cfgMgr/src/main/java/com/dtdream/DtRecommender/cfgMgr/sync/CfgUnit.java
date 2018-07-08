package com.dtdream.DtRecommender.cfgMgr.sync;

import com.dtdream.DtRecommender.cfgMgr.CfgMgrException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CfgUnit {
    private String name;
    private byte type;

    private ZooKeeper zk;
    private String path;

    public CfgUnit(ZooKeeper zk, String path) throws CfgMgrException.NotExistException {
        this.zk = zk;
        this.path = path;
        this.name = path.substring(path.lastIndexOf("/")+1);

        Stat stat = new Stat();
        try {
            // 第一个byte代表类型
            byte[] bytes = zk.getData(path, true, stat);
            type = bytes[0];

            // TODO 异常处理还需细化
        } catch (KeeperException.NoNodeException e) {
            throw new CfgMgrException.NotExistException();
        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CfgUnit(ZooKeeper zk, String path, byte type) throws CfgMgrException.NotExistException {
        this.type = type;
        this.zk = zk;
        this.path = path;
        this.name = path.substring(path.lastIndexOf("/")+1);

        try {
            // 第一个byte代表类型
            byte[] bytes = new byte[1];
            bytes[0] = type;
            zk.setData(path, bytes, -1);

            // TODO 异常处理还需细化
        } catch (KeeperException.NoNodeException e) {
            throw new CfgMgrException.NotExistException();
        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public CfgUnit getParentUnit() throws CfgMgrException.NotExistException {
        int index = path.lastIndexOf("/");
        if (index <= 0) {
            throw new CfgMgrException.NotExistException();
        }

        return new CfgUnit(zk, path.substring(0, index));
    }

    public CfgUnit getChildUnit(String chileName) throws CfgMgrException.NotExistException {
        return new CfgUnit(zk, path + "/" + chileName);
    }

    public List<CfgUnit> getChildsUnit() {
        List<CfgUnit> list = new LinkedList<CfgUnit>();

        try {
            List<String> childs = zk.getChildren(path, true);
            for (String c : childs) {
                try {
                    list.add(new CfgUnit(zk, path + "/" + c));
                } catch (CfgMgrException.NotExistException e) {
                    e.printStackTrace();
                }
            }

            // TODO 异常处理还需细化
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list;
    }

    public CfgUnit createChildUnit(String childName, byte childType) throws CfgMgrException.ExistException {
        if (null == childName || "".equals(name)) {
            throw new InvalidParameterException();
        }

        CfgUnit cfgUnit = null;

        // 第一个byte代表类型
        byte[] bytes = new byte[1];
        bytes[0] = childType;
        try {
            String childPath = path + "/" + childName;
            zk.create(childPath, bytes, null, CreateMode.PERSISTENT);
            cfgUnit = new CfgUnit(zk, childPath);

            // TODO 异常处理还需细化
        } catch (KeeperException.NodeExistsException e) {
            throw new CfgMgrException.ExistException();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CfgMgrException.NotExistException e) {
            e.printStackTrace();
        }

        return cfgUnit;
    }

    public void deleteChildUnit(String childName) {
        if (null == childName || "".equals(name)) {
            throw new InvalidParameterException();
        }

        String childPath = path + "/" + childName;
        try {
            zk.delete(childPath, -1);

            // TODO 异常处理还需细化
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

        return;
    }

    public byte[] readCfgData() {
        byte[] data = null;
        Stat stat = new Stat();
        try {
            byte[] bytes = zk.getData(path, true, stat);

            // 第一个byte代表类型需要跳过
            data = new byte[bytes.length-1];
            System.arraycopy(bytes, 1, data, 0, data.length);

            // TODO 异常处理还需细化
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void writeCfgData(byte[] bytes) {
        if (null == bytes || 0 == bytes.length) {
            throw new InvalidParameterException();
        }

        byte[] data = new byte[bytes.length+1];
        data[0] = type;
        System.arraycopy(bytes, 0, data, 1, data.length);
        try {
            zk.setData(path, data, -1);

            // TODO 异常处理还需细化
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
