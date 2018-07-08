package com.dtdream.DtRecommender.cfgMgr.entity.biz;

import com.dtdream.DtRecommender.cfgMgr.zk.PolicyItem;
import com.dtdream.DtRecommender.cfgMgr.zk.ScnItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by handou on 10/10/16.
 * zookeeper 场景节点的逻辑结构，用于存放于内存中
 *
 */
public class RScene {
    private ScnItem scn_data;
    private Map<String, PolicyItem> policyItems;   /* 场景节点下的所有场景子节点 */

    public RScene(ScnItem scn_data) {
        this.scn_data = scn_data;
        this.policyItems = new HashMap<String, PolicyItem>();
    }

    public ScnItem getScn_data() {
        return scn_data;
    }

    public void setScn_data(ScnItem scn_data) {
        this.scn_data = scn_data;
    }

    public Map<String, PolicyItem> getPolicyItems() {
        return policyItems;
    }

    public void setPolicyItems(Map<String, PolicyItem> policyItems) {
        this.policyItems = policyItems;
    }
}
