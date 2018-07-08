package com.dtdream.DtRecommender.common.model.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by handou on 9/21/16.
 */
public class UserList implements Serializable {
    private List<User>  users;

    public UserList() {
    }

    public UserList(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
