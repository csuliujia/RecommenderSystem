package com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData;

import java.io.Serializable;

/**
 * Created by handou on 10/11/16.
 * zookeeper 业务节点存放的data
 *
 */
public class BizItem implements Serializable {
    private String biz_name;
    private String biz_code;
    private String description;
    private String offline_name;
    private String kvStore_name;
    private String dbRec_name;
    private String mqRec_name;

    public BizItem() {
    }

    public BizItem(String biz_name, String biz_code, String description) {
        this.biz_name = biz_name;
        this.biz_code = biz_code;
        this.description = description;
    }

    public BizItem(String biz_name, String biz_code, String description, String offline_name, String kvStore_name, String dbRec_name, String mqRec_name) {
        this.biz_name = biz_name;
        this.biz_code = biz_code;
        this.description = description;
        this.offline_name = offline_name;
        this.kvStore_name = kvStore_name;
        this.dbRec_name = dbRec_name;
        this.mqRec_name = mqRec_name;
    }

    public String getBiz_name() {
        return biz_name;
    }

    public void setBiz_name(String biz_name) {
        this.biz_name = biz_name;
    }

    public String getBiz_code() {
        return biz_code;
    }

    public void setBiz_code(String biz_code) {
        this.biz_code = biz_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOffline_name() {
        return offline_name;
    }

    public void setOffline_name(String offline_name) {
        this.offline_name = offline_name;
    }

    public String getKvStore_name() {
        return kvStore_name;
    }

    public void setKvStore_name(String kvStore_name) {
        this.kvStore_name = kvStore_name;
    }

    public String getDbRec_name() {
        return dbRec_name;
    }

    public void setDbRec_name(String dbRec_name) {
        this.dbRec_name = dbRec_name;
    }

    public String getMqRec_name() {
        return mqRec_name;
    }

    public void setMqRec_name(String mqRec_name) {
        this.mqRec_name = mqRec_name;
    }
}
