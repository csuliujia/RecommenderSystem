package com.dtdream.DtRecommender.cfgMgr;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by yzt on 16-10-14.
 */
public class Utils {
    /**
     * @return 返回一个连接已建立的ZooKeeper对象。不必考虑异步建立连接的问题。
     */
    public static ZooKeeper createZooKeeper() {
        final CountDownLatch lock = new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("10.99.3.35:2181", 1000, new Watcher() {
                public void process(WatchedEvent event) {
                    System.out.println(event.getState());
                    if (event.getState().equals(Event.KeeperState.SyncConnected))
                        lock.countDown();
                }
            });
            lock.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }
}
