package com.dtdream.DtRecommender.cfgMgr.impl.policy.node;


import com.dtdream.DtRecommender.common.model.recommender.ItemModel;

import java.util.List;
import java.util.Map;

/**
 * 合并两个推荐列表
 */
public class Merge extends AbstractNode {
    public Merge(int id, Map<String, String> params) {
        super(id, params);
    }

    @Override
    public List<ItemModel> run(String bizCode, String objId, List<List<ItemModel>> inputRecList) {
        return null;
    }
}
