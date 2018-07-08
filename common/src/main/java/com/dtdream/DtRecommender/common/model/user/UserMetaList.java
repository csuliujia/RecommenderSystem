package com.dtdream.DtRecommender.common.model.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by handou on 9/26/16.
 */
public class UserMetaList implements Serializable {
    private List<UserMeta> usres;

    public UserMetaList(List<UserMeta> usres) {
        this.usres = usres;
    }

    public UserMetaList() {
    }

    public List<UserMeta> getUsres() {
        return usres;
    }

    public void setUsres(List<UserMeta> usres) {
        this.usres = usres;
    }
}
