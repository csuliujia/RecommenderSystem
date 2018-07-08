package com.dtdream.DtRecommender.cfgMgr.impl.policy;

import com.dtdream.DtRecommender.cfgMgr.Policy;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.*;

import java.util.concurrent.LinkedBlockingQueue;

import static com.dtdream.DtRecommender.cfgMgr.Utils.createZooKeeper;

public class PolicyCfgImplTest {
    private static PolicyCfgImpl policyCfg;
    private static ZooKeeper zk;
    private static final String name_create = "p1";
    private static final String name_delete = "p2323";
    private static final String name_get = "p2";
    private static final String name_publish = "p4";
    private static final String info = "This is a test";
    private static final String path = "/policy/";

    @BeforeClass
    public static void initial() throws Exception {
        zk = createZooKeeper();
        if (zk.exists(path + name_create, false) != null)
            zk.delete(path + name_create, -1);//删除一个节点用于创建
        if (zk.exists(path + name_delete, false) == null)
            zk.create(path + name_delete, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//创建一个节点用于测试删除
        if (zk.exists(path + name_publish, false) == null)
            zk.create(path + name_publish, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//创建一个节点用于发布
        policyCfg = new PolicyCfgImpl(zk);
    }

    @AfterClass
    public static void clear() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createPolicy() throws Exception {
        Policy p1 = policyCfg.createPolicy(name_create, info);
        Assert.assertNotNull(p1);

        Policy p2 = policyCfg.createPolicy(name_create, info);
        Assert.assertNull(p2);



    }

    @Test
    public void getPolicy() throws Exception {
        Policy p = policyCfg.getPolicy(name_get);
        Assert.assertNotNull(p);
    }


    @Test
    public void publishPolicy() throws Exception {
        Stat bs = new Stat();
        Stat as = new Stat();
        zk.getData(path + name_publish, null, bs);
        Policy p = policyCfg.getPolicy(name_publish);
        policyCfg.publishPolicy(p);
        zk.getData(path + name_publish, null, as);
        Assert.assertEquals(as.getVersion() - bs.getVersion(), 1);//每次修改znode，version版本号会增1
    }

    @Test
    @Ignore
    public void runPolicy() throws Exception {

    }

    @Test
    @Ignore
    public void runPolicy1() throws Exception {

    }

    @Test
    public void deletePolicy() throws Exception {
        policyCfg.deletePolicy(name_delete);
        Stat s = zk.exists(path + name_delete, null);
        Assert.assertNull(s);
    }

    /**
     * 综合测试，使用一个线程进行Policy的增删改操作，另一个线程观测获取到的Policy是否发生变化。模拟两台控制台服务器和API Server
     * 由于run方法未完成，无法检测结果变化。
     * //TODO JUnit不能直接支持多线程测试，子线程的Assert不能影响最终的测试结果，考虑TestNG或者使用其他方法
     */
    @Test
    public void watch() throws Exception {
        final ZooKeeper zk_Watch = createZooKeeper();
        final PolicyCfgImpl policyCfg_watch = new PolicyCfgImpl(zk_Watch);//API Server
        final String name = "name";
        final LinkedBlockingQueue<String> producer = new LinkedBlockingQueue<String>(1);
        final LinkedBlockingQueue<String> consumer = new LinkedBlockingQueue<String>(1);
        //清理
        if (zk.exists(path + name, false) != null)
            zk.delete(path + name, -1);

        Thread configurator = new Thread() {
            public void run() {
                try {
                    policyCfg_watch.createPolicy(name, name);
                    producer.put(name);//唤醒消费者

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    consumer.take();
                    policyCfg_watch.deletePolicy(name);
                    producer.put(name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread apiServer = new Thread() {
            public void run() {
                try {
                    producer.take();
                    sleep(10);//为watch回调修改本地数据预留时间
                    consumer.put(name);  //唤醒生产者

                    producer.take();
                    sleep(10);//为watch回调修改本地数据预留时间

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        apiServer.start();
        configurator.start();
        apiServer.join();
        configurator.join();
        zk_Watch.close();
    }

}