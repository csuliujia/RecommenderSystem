package com.dtdream.DtRecommender.server.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/9/27.
 */
@Entity
@Table(name = "item_prop")
public class ItemMetaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "prop_name", nullable = false)
    private String name;

    @Column(name = "prop_value", nullable = false)
    private String value;

    public ItemMetaEntity() {
    }

    public ItemMetaEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
