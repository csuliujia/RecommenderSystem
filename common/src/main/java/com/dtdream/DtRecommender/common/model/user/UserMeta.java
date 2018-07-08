package com.dtdream.DtRecommender.common.model.user;

import com.dtdream.DtRecommender.common.model.meta.EntityAttributeType;

import java.io.Serializable;

/**
 * Created by handou on 9/26/16.
 */
public class UserMeta implements Serializable {
    private String name;    /* 属性的Key */
    private EntityAttributeType type;    /* 属性的类型，见 EntityAttributeType */

    public UserMeta(String name, EntityAttributeType type) {
        this.name = name;
        this.type = type;
    }

    public UserMeta() {
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
}
