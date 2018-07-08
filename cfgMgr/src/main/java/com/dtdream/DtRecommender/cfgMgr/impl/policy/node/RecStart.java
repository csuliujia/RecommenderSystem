package com.dtdream.DtRecommender.cfgMgr.impl.policy.node;

import com.dtdream.DtRecommender.common.model.recommender.ItemModel;

import java.util.List;
import java.util.Map;

/**
 * 开始节点
 */
public class RecStart extends AbstractNode {
    public RecStart(int id, Map<String, String> params) {
        super(id, params);
    }

    public List<ItemModel> run(String bizCode, String objId, List<List<ItemModel>> inputRecList) {
        return null;
    }


}
