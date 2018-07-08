package com.dtdream.DtRecommender.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "youyou")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String user;

    @Column(name = "tags", nullable = true)
    private String tags;

    public UserEntity() {
    }

    public String getUser_id() {
        return user;
    }

    public void setUser_id(String user_id) {
        this.user = user_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public UserEntity(String user_id, String tags) {
        this.user = user_id;
        this.tags = tags;
    }
}
