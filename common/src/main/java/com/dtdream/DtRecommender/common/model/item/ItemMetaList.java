package com.dtdream.DtRecommender.common.model.item;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class ItemMetaList implements Serializable{
    private List<ItemMeta> items;

    public ItemMetaList() {
    }

    public ItemMetaList(List<ItemMeta> items) {
        this.items = items;
    }

    public void setItems(List<ItemMeta> items) {
        this.items = items;
    }

    public List<ItemMeta> getItems() {
        return items;
    }
}
