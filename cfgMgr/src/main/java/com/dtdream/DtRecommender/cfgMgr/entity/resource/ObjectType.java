package com.dtdream.DtRecommender.cfgMgr.entity.resource;

/**
 *
 */
public enum  ObjectType {
     RESOURCECFG("RESOURCECFG"),
     OFFLINE("OFFLINE"),
     KVSTORE("KVSTORE"),
     DB("DB"),
     MQ("MQ");

    private String type;

    ObjectType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
