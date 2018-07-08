package com.dtdream.DtRecommender.cfgMgr.impl.policy.node;

import com.dtdream.DtRecommender.common.model.recommender.ItemModel;

import java.util.List;
import java.util.Map;

/**
 * 结束节点
 */
public class RecEnd extends AbstractNode {
    public RecEnd(int id, Map<String, String> params) {
        super(id, params);
    }

    public List<ItemModel> run(String bizCode, String objId, List<List<ItemModel>> inputRecList) {
        return inputRecList.get(0);//将上一个节点的输出直接返回。
    }


}