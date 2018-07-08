package com.dtdream.DtRecommender.common.model.item;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ItemIdList implements Serializable {
    List<String> items;

    public ItemIdList() {
    }

    public ItemIdList(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

}
