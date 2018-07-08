package com.dtdream.DtRecommender.cfgMgr.impl.resource;

import com.dtdream.DtRecommender.cfgMgr.entity.resource.OfflineRec;
import org.apache.zookeeper.ZooKeeper;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceCfgImplTest {
    static ResourceCfgImpl resourceCfg;
    static ZooKeeper zooKeeper;
    static OfflineRec olRec;
    static final String name = "ODPS_name_1";
    static final String type = "OFFLINE";

    @BeforeClass
    public static void initial() throws Exception {
        zooKeeper = new ZooKeeper("localhost:2181", 1000, null);
        resourceCfg = new ResourceCfgImpl(zooKeeper);
    }

    @AfterClass
    public static void clear() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test

    public void test01_createOfflineRec() {
        olRec = new OfflineRec(name, "description_1","project_name_1","1","1","1","1");
        resourceCfg.createOfflineRec(olRec);
    }

    @Test
    @Ignore
    public void test02_updateOfflineRec() {
        olRec = new OfflineRec(name, "description_2","project_name_2","2","2","2","2");
        resourceCfg.updateOfflineRec(olRec);
    }

    @Test
    @Ignore
    public void test03_getOfflineRec() throws Exception {
        OfflineRec offlineRec =resourceCfg.getOfflineRec(name);
        Assert.assertNotNull("获取单个offline资源失败", offlineRec);
        Assert.assertEquals(offlineRec.getDescription(), "description_2");
    }

    @Test
    @Ignore
    public void test04_getOfflineRecList() throws Exception {
        List<OfflineRec> offlineList = resourceCfg.getOfflineRecList(type);
        Assert.assertNotNull("获取offline资源列表失败", offlineList);
    }

    @Test

    public void test05_deleteOfflineRec() {
        resourceCfg.deleteOfflineRec(name);
    }

}
