package com.dtdream.DtRecommender.common.model.item;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/21.
 */
public class Item implements Serializable {
    private String item_id;
    private String category;
    private String keywords;
    private String description;
    private String properties;
    private String bizinfo;

    public Item(String item_id, String category, String keywords, String description, String properties, String bizinfo) {
        this.item_id = item_id;
        this.category = category;
        this.keywords = keywords;
        this.description = description;
        this.properties = properties;
        this.bizinfo = bizinfo;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getBizinfo() {
        return bizinfo;
    }

    public void setBizinfo(String bizinfo) {
        this.bizinfo = bizinfo;
    }

}


