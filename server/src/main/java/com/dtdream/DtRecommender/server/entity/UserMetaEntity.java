package com.dtdream.DtRecommender.server.entity;

import javax.persistence.*;

/**
 * Created by handou on 9/26/16.
 */
@Entity
@Table(name = "user_prop_meta")
public class UserMetaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    public UserMetaEntity() {
    }

    public UserMetaEntity(String name, String type) {
        super();

        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
