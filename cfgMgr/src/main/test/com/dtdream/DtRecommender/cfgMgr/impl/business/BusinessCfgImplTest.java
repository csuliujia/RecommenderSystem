package com.dtdream.DtRecommender.cfgMgr.impl.business;

import com.dtdream.DtRecommender.cfgMgr.Policy;
import com.dtdream.DtRecommender.cfgMgr.impl.policy.PolicyCfgImpl;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.dtdream.DtRecommender.cfgMgr.Utils.createZooKeeper;

/**
 * Created by handou on 10/15/16.
 */
public class BusinessCfgImplTest {
 /*   private static PolicyCfgImpl policyCfg;
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
*/
}




