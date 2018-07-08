package com.dtdream.DtRecommender.cfgMgr.entity.biz;

import com.dtdream.DtRecommender.cfgMgr.zk.BizItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by handou on 10/10/16.
 * zookeeper 业务节点的逻辑结构，用于存放于内存中
 *
 */
public class RBiz {
    private BizItem biz_data;
    private Map<String, RScene> scnList;    /* 业务节点下的所有场景子节点 */

    public RBiz() {
    }

    public RBiz(BizItem biz_data) {
        this.biz_data = biz_data;
        this.scnList = new HashMap<String, RScene>();
    }

    public BizItem getBiz_data() {
        return biz_data;
    }

    public void setBiz_data(BizItem biz_data) {
        this.biz_data = biz_data;
    }

    public Map<String, RScene> getScnList() {
        return scnList;
    }

    public void setScnList(Map<String, RScene> scnList) {
        this.scnList = scnList;
    }
}
