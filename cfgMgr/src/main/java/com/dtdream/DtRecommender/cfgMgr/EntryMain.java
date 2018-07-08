package com.dtdream.DtRecommender.cfgMgr;

import com.dtdream.DtRecommender.cfgMgr.utils.PathHelper;
import com.dtdream.DtRecommender.cfgMgr.zk.PolicyItem;
import com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod;
import com.dtdream.DtRecommender.common.util.LogHelper;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.exists;
import static java.lang.Thread.sleep;

/**
 * Created by handou on 10/15/16.
 */
public class EntryMain {
    private final static Logger log = LoggerFactory.getLogger(EntryMain.class);

    public static ZooKeeper createZooKeeper() {
        final CountDownLatch lock = new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper("10.99.3.35:2181", 300000, new Watcher() {
                public void process(WatchedEvent event) {
                    log.info("+++++++++ main watch: {}", event.getState());

                    if (event.getState().equals(Event.KeeperState.SyncConnected))
                        lock.countDown();
                }
            });

            lock.await();
        } catch (Exception e) {
            System.out.println(" crate zookeeper faild. ");

            e.printStackTrace();
        }

        return zooKeeper;
    }


    public static void main(String[] args) {

                /* 日志路径配置 */
        LogHelper.configLogPath("cfgMgr");

        ZooKeeper zk = createZooKeeper();

        CfgMgr mgr = new CfgMgr();
        mgr.init(zk);

/*        ZkMethod.deleteGroup(zk, "/bizcfg/car_code/main_page");
        ZkMethod.deleteGroup(zk, "/bizcfg/car_code");
        ZkMethod.deleteGroup(zk, "/bizcfg");*/

        log.info("#############   main 000  create root /bizcfg");
        BusinessCfg bizCfg = mgr.getBusinessCfg();
        log.info("#############   main 000  create root /bizcfg  over");

        log.info("main 001  create biz ");

        Business biz = bizCfg.createBiz("car_code", "geely_recommend", "hava  a try");


        log.info("main 001  create biz  over");

        try {
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        log.info("main 002  create scn ");

/*        while (!bizCfg.getBizList().contains(biz.getBiz_code())) {
            log.info("main creating biz {}  wait for ..... ", biz.getBiz_code());
        }*/

        Scene scn = biz.createScn("main_page", "boyue_suv", "shining you eye");
        log.info("main 002  create scn over ");

        log.info("main 003 create policy");

/*        while (!biz.getScnList().contains(scn.getScn_code())) {
            log.info("main creating scn {}  wait for ..... ", scn.getScn_code());
        }*/

        try {
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scn.createPolicy("user_algorith", 0.7);
        log.info("main 003 create policy over");

        try {
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        scn.createPolicy("item_algorith", 0.3);
        log.info("main 004  create policy");

        try {
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("main 005  update biz");
        biz.setDescription("伐木累");
        log.info("main 005  update biz over");

        PolicyItem i = ZkMethod.getData(zk, "/bizcfg/car_code/main_page/user_algorith", null, PolicyItem.class);

        log.info("name: {}  ration: {}" , i.getPolicy_name(), i.getFlow_ratio());

        log.info("before: {}", scn.getDescription());
        scn.setDescription(" just do it");

        log.info("after: {}", scn.getDescription());


        /* 清除环境 */
/*        ZkMethod.deleteGroup(zk, "/bizcfg/car_code/main_page/user_algorith");
        ZkMethod.deleteGroup(zk, "/bizcfg/car_code/main_page");
        ZkMethod.deleteGroup(zk, "/bizcfg/car_code");
        ZkMethod.deleteGroup(zk, "/bizcfg");*/

    }


}
