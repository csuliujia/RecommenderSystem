package com.dtdream.DtRecommender.common.model.item;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ItemList {
    private List<Item> items;

    public ItemList(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
