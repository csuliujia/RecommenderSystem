package com.dtdream.DtRecommender.common.model.item;

import com.dtdream.DtRecommender.common.model.meta.EntityAttributeType;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/27.
 */
public class ItemMeta implements Serializable {
    private String name;
    private EntityAttributeType type;

    public ItemMeta(String name, EntityAttributeType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityAttributeType getType() {
        return type;
    }

    public void setType(EntityAttributeType type) {
        this.type = type;
    }

    public ItemMeta() {
    }
}
