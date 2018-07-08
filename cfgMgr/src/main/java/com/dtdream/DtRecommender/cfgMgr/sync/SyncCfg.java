package com.dtdream.DtRecommender.cfgMgr.sync;

import com.dtdream.DtRecommender.cfgMgr.CfgMgrException;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/18.
 */
public class SyncCfg {
    private String zkConStr;
    private ZooKeeper zk;
    private CfgWatcher watcher;
    private Map<String, CfgChangeNotify> notifyMap;

    public SyncCfg(String zkConStr) throws IOException {
        notifyMap = new HashMap<String, CfgChangeNotify>();
        this.zkConStr = zkConStr;
        initZK();
    }

    public CfgUnit createRootCfgUnit(String name, CfgChangeNotify notify) throws CfgMgrException.ExistException {
        String path = "/" + name;
        CfgUnit cfgUnit = null;
        try {
            zk.create(path, null, null, CreateMode.PERSISTENT);
            notifyMap.put(name, notify);
            cfgUnit = new CfgUnit(zk, path, (byte)-1);

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

    public CfgUnit getRootCfgUnit(String name, CfgChangeNotify notify) throws CfgMgrException.NotExistException {
        String path = "/" + name;

        CfgUnit cfgUnit =  new CfgUnit(zk, path);
        notifyMap.put(name, notify);
        return cfgUnit;
    }

    public void deleteRootCfgUnit(String name) throws CfgMgrException.NotEmptyException {
        String path = "/" + name;

        try {
            zk.delete(path, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException.NotEmptyException e) {
            throw new CfgMgrException.NotEmptyException();
        }catch (KeeperException e) {
            e.printStackTrace();
        }

        return ;
    }


    private void initZK() throws IOException {
        if (null != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        zk = new ZooKeeper(zkConStr, 30000, this.watcher);
    }

    private String getRootName(String path) {
        if (null == path) {
            return null;
        }

        String sub = path.substring(1);
        return sub.substring(0, sub.indexOf('/'));
    }

    private void batch() {
        for (Map.Entry<String, CfgChangeNotify> entry: notifyMap.entrySet()) {
            String path = "/" + entry.getKey();
            try {
                entry.getValue().batch(new CfgUnit(zk, path));
            } catch (CfgMgrException.NotExistException e) {
                // TODO 输出错误日志
                e.printStackTrace();
            }
        }

        return;
    }

    private void nodeCreate(String path) {
        // Noting
    }

    private void childChange(String path) {
        String rootName = getRootName(path);
        CfgChangeNotify notify = notifyMap.get(rootName);
        if (null != null) {
            try {
                notify.cfgChildChange(new CfgUnit(zk, path));
            } catch (CfgMgrException.NotExistException e) {
                // TODO 输出错误日志
                e.printStackTrace();
            }
        }
    }

    private void dataChange(String path) {
        String rootName = getRootName(path);
        CfgChangeNotify notify = notifyMap.get(rootName);
        if (null != null) {
            try {
                notify.cfgDataChange(new CfgUnit(zk, path));
            } catch (CfgMgrException.NotExistException e) {
                // TODO 输出错误日志
                e.printStackTrace();
            }
        }
    }

    private void nodeDelete(String path) {
        // Noting
    }


    private class CfgWatcher implements Watcher {

        public void process(WatchedEvent event) {
            Event.KeeperState state = event.getState();
            Event.EventType eType = event.getType();
            String path = event.getPath();

            if (Event.KeeperState.Expired == state) {
                try {
                    initZK();
                } catch (IOException e) {
                    // 输出错误日志
                    e.printStackTrace();
                }
            }

            if (Event.KeeperState.SyncConnected != state) {
                return;
            }

            switch (eType) {
                case None:
                    // zookeeper重新连接，触发批量同步
                    batch();
                    break;

                case NodeChildrenChanged:
                    childChange(path);
                    break;

                case NodeCreated:
                    nodeCreate(path);
                    break;

                case NodeDataChanged:
                    dataChange(path);
                    break;

                case NodeDeleted:
                    nodeDelete(path);
                    break;
            }

        }
    }

}
