package com.dtdream.DtRecommender.cfgMgr.impl.biz;

import com.dtdream.DtRecommender.cfgMgr.PolicyConf;
import com.dtdream.DtRecommender.cfgMgr.utils.PathHelper;
import com.dtdream.DtRecommender.cfgMgr.zk.PolicyItem;
import com.dtdream.DtRecommender.cfgMgr.zk.ZNodePath;
import com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper.toBytes;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZNodePath.PATH_BIZCFG_ROOT;

/**
 * Created by handou on 10/11/16.
 */
public class PolicyConfImpl implements PolicyConf {
    private final static Logger log = LoggerFactory.getLogger(PolicyConfImpl.class);
    private ZooKeeper zk;
    private String biz_code;
    private String scn_code;

    private PolicyItem item;

    private void createPolicyConfNode() {
        byte[] data = toBytes(item);
        String path = PathHelper.getPath(ZNodePath.PATH_BIZCFG_ROOT, biz_code, scn_code, item.getPolicy_name());

        ZkMethod.createNormalNode(zk, path, data);
    }

    public PolicyConfImpl(ZooKeeper zk, String biz_code, String scn_code, String policy_name, double flow_ratio) {
        this.zk = zk;
        this.biz_code = biz_code;
        this.scn_code = scn_code;

        this.item = new PolicyItem(policy_name, flow_ratio);

        createPolicyConfNode();
    }

    private void updatePolicyNode(){
        byte[] data = toBytes(item);
        String path = PathHelper.getPath(PATH_BIZCFG_ROOT, biz_code, scn_code, item.getPolicy_name());

        ZkMethod.updateData(zk, path, data, null);
    }

    public String getPolicy_name() {
        return item.getPolicy_name();
    }

    public double getFlow_ratio() {
        return item.getFlow_ratio();
    }

    public void setFlow_ratio(double flow_ratio) {
        item.setFlow_ratio(flow_ratio);

        updatePolicyNode();
    }
}
