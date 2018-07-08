package com.dtdream.DtRecommender.common.model.recommender;

import com.dtdream.DtRecommender.common.model.recommender.ItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */

public class RecommenderItems {
    private  String trace_id;
    private  List<ItemModel> rec;

    public RecommenderItems(String trace_id, List<ItemModel> rec) {
        this.trace_id = trace_id;
        this.rec = rec;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }

    public List<ItemModel> getRec() {
        return rec;
    }

    public void setRec(List<ItemModel> rec) {
        this.rec = rec;
    }
}
