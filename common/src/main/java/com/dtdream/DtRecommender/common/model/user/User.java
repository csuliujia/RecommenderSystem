package com.dtdream.DtRecommender.common.model.user;

import java.io.Serializable;

/**
 * Created by handou on 9/21/16.
 */
public class User implements Serializable {
    private String user_id;
    private String tags;

    public User(String user_id, String tags) {
        this.user_id = user_id;
        this.tags = tags;
    }

    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
