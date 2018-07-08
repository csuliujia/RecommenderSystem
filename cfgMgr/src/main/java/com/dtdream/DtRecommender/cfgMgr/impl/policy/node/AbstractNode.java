package com.dtdream.DtRecommender.cfgMgr.impl.policy.node;


import com.dtdream.DtRecommender.common.model.recommender.ItemModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractNode implements Serializable {
    private int id;

    Map<String, String> params;

    public AbstractNode(int id, Map<String, String> params) {
        this.id = id;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public abstract List<ItemModel> run(String bizCode, String objId, List<List<ItemModel>> inputRecList);

    public int getId() {
        return id;
    }
}
