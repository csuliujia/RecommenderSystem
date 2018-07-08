package com.dtdream.DtRecommender.common.model.userbehavior;

import java.io.Serializable;

/**
 * Created by handou on 9/7/16.
 */
public enum Bhv_Type implements Serializable {
    VIEW,
    CLICK,
    COLLECT,
    UNCLOLLECT,
    SEARCH_CLICK,
    COMMENT,
    SHARE,
    LIKE,
    DISLIKE,
    GRADE,
    CONSUME,
    USE
}
