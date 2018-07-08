package com.dtdream.DtRecommender.common.model.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by handou on 9/23/16.
 */
public class UserIdList implements Serializable {
    private List<String> users;

    public UserIdList(List<String> users) {
        this.users = users;
    }

    public UserIdList() {
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
