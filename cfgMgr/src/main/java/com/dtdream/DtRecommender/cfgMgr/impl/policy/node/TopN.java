package com.dtdream.DtRecommender.cfgMgr.impl.policy.node;


import com.dtdream.DtRecommender.common.model.recommender.ItemModel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 取推荐结果的前N个
 */
public class TopN extends AbstractNode {
    private static final String N = "N";
    public TopN(int id, Map<String, String> params) {
        super(id, params);
    }

    public List<ItemModel> run(String bizCode, String objId, List<List<ItemModel>> inputRecList) {
        int n = Integer.parseInt(super.params.get(N));
        LinkedList<ItemModel> rs = new LinkedList<ItemModel>();
        Iterator<ItemModel> iterator = inputRecList.get(0).iterator();//应该只有一个输入表
        for (int i = 0; i < n; i++) {
            rs.add(iterator.next());
        }
        return rs;
    }


}
