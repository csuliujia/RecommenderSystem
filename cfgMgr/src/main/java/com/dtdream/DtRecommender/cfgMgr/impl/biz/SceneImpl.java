package com.dtdream.DtRecommender.cfgMgr.impl.biz;


import com.dtdream.DtRecommender.cfgMgr.PolicyConf;
import com.dtdream.DtRecommender.cfgMgr.Scene;
import com.dtdream.DtRecommender.cfgMgr.utils.PathHelper;
import com.dtdream.DtRecommender.cfgMgr.zk.PolicyItem;
import com.dtdream.DtRecommender.cfgMgr.zk.ScnItem;
import com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper.toBytes;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZNodePath.PATH_BIZCFG_ROOT;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.createNormalNode;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.updateData;

/**
 * Created by handou on 10/11/16.
 */
public class SceneImpl implements Scene{
    private final static Logger log = LoggerFactory.getLogger(SceneImpl.class);
    private ZooKeeper zk;

    private String biz_code;

    private ScnItem item;

    /* 该业务下所有场景的内存数据 */
    private Map<String, PolicyItem> rPolicies_data;

    private void createScnNode() {
        byte[] data = toBytes(item);
        String path = PathHelper.getPath(PATH_BIZCFG_ROOT, biz_code, item.getScn_code());

        createNormalNode(zk, path, data);
    }

    public SceneImpl(ZooKeeper zk, String biz_code, String scn_code, String scn_name, String description) {
        this.zk = zk;
        this.biz_code = biz_code;

        this.item = new ScnItem(scn_code, scn_name, description);

        /* 向zookeeper树中 添加该节点 */
        createScnNode();
    }

    private void updateScnNode(){

        byte[] data = toBytes(item);
        String path = PathHelper.getPath(PATH_BIZCFG_ROOT, biz_code, item.getScn_code());
        updateData(zk, path, data, null);
    }

    public String getScn_name() {
        return this.item.getScn_name();
    }

    public void setScn_name(String scn_name) {

        item.setScn_name(scn_name);
        updateScnNode();
    }

    public String getScn_code() {
        return item.getScn_code();
    }

    public void setScn_code(String scn_code) {

        item.setScn_code(scn_code);
        updateScnNode();
    }

    public String getDescription() {
        return item.getDescription();
    }

    public void setDescription(String description) {

        item.setDescription(description);
        updateScnNode();
    }

    public PolicyConf createPolicy(String name, double flow_ratio) {
        return new PolicyConfImpl(zk, biz_code, getScn_code(), name, flow_ratio);
    }

    public void deletePolicy(String name) {
        rPolicies_data.remove(name);
    }

    public List<String> getPolicyList() {
        return new ArrayList<String>(rPolicies_data.keySet());
    }
}
