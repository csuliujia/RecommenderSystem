package com.dtdream.DtRecommender.server.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/9/29.
 */
@Entity
@Table(name = "rec_item")
public class RecItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "item_info", nullable = true)
    private String itemInfo;

    public RecItemEntity() {
    }

    public RecItemEntity(String itemId, String itemInfo) {
        this.itemId = itemId;
        this.itemInfo = itemInfo;
    }

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

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

}
