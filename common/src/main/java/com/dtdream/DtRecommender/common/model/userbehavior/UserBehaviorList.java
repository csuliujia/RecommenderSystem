package com.dtdream.DtRecommender.common.model.userbehavior;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class UserBehaviorList implements Serializable {
    private List<UserBehavior> actions;

    public UserBehaviorList(List<UserBehavior> actions) {
        this.actions = actions;
    }

    public UserBehaviorList() {
    }

    public List<UserBehavior> getActions() {
        return actions;
    }

    public void setActions(List<UserBehavior> actions) {
        this.actions = actions;
    }
}
