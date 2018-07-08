package com.dtdream.DtRecommender.common.model.meta;

/**
 * Created by handou on 9/26/16.
 */
public enum EntityAttributeType {
    SV_ENUM("sv_enum"),  /* 单值枚举型 */
    SV_NUM("sv_num"),   /* 单值数值型 */
    MV_ENUM("mv_enum"),  /* 多值枚举型 */
    KV_NUM("kv_num");   /* KV数值型 */

    private String name;

    EntityAttributeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
