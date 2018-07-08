package com.dtdream.DtRecommender.cfgMgr;

import com.dtdream.DtRecommender.cfgMgr.impl.biz.BusinessCfgImpl;
import com.dtdream.DtRecommender.cfgMgr.impl.policy.PolicyCfgImpl;
import com.dtdream.DtRecommender.cfgMgr.impl.resource.ResourceCfgImpl;
import org.apache.zookeeper.ZooKeeper;


public class CfgMgr {

    private ZooKeeper zk;
    private static CfgMgr inst = new CfgMgr();
    private static ResourceCfg resourceCfg;
    private static BusinessCfg businessCfg;
    private static PolicyCfg policyCfg;

    public CfgMgr() {

    }

    public static CfgMgr getInst() {
        return inst;
    }

    /* 使用者，读取配置文件，创建Zookeeper对象,本lib不需要自己创建Zookeeper对象 */
    public void init(ZooKeeper zk) {
        this.zk = zk;
        resourceCfg = new ResourceCfgImpl(this.zk);
        businessCfg = new BusinessCfgImpl(this.zk);
        policyCfg = new PolicyCfgImpl(zk);
    }

    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ResourceCfg getResourceCfg() {
        return resourceCfg;
    }

    public BusinessCfg getBusinessCfg() {
        return businessCfg;
    }

    public PolicyCfg getPolicyCfg() {
        return policyCfg;
    }
}
