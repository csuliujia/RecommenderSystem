package com.dtdream.DtRecommender.server.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/9/26.
 */
@Entity
@Table(name = "car")
public class ItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "keywords", nullable = true)
    private String keywords;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "properties", nullable = true)
    private String properties;

    @Column(name = "bizinfo", nullable = true)
    private String bizinfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public ItemEntity() {
    }

    public ItemEntity(String itemId, String category, String keywords, String description, String properties, String bizinfo) {

        this.itemId = itemId;
        this.category = category;
        this.keywords = keywords;
        this.description = description;
        this.properties = properties;
        this.bizinfo = bizinfo;
    }
}
