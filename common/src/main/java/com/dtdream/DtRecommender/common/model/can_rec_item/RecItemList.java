package com.dtdream.DtRecommender.common.model.can_rec_item;

import java.io.Serializable;
import java.util.List;

/**
 * Created by handou on 9/29/16.
 */
public class RecItemList implements Serializable {
    private List<RecItem> rec_items;


    public RecItemList() {
    }

    public RecItemList(List<RecItem> rec_items) {
        this.rec_items = rec_items;
    }

    public List<RecItem> getRec_items() {
        return rec_items;
    }

    public void setRec_items(List<RecItem> rec_items) {
        this.rec_items = rec_items;
    }
}
