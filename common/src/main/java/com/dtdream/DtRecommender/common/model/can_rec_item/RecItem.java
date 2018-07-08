package com.dtdream.DtRecommender.common.model.can_rec_item;

import java.io.Serializable;

/**
 * Created by handou on 9/29/16.
 */
public class RecItem implements Serializable {
    private String item_id;
    private String item_info;

    public RecItem() {
    }

    public RecItem(String item_id, String item_info) {
        this.item_id = item_id;
        this.item_info = item_info;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_info() {
        return item_info;
    }

    public void setItem_info(String item_info) {
        this.item_info = item_info;
    }
}
