package com.dtdream.DtRecommender.server.dao;


import com.dtdream.DtRecommender.common.model.userbehavior.UserBehaviorList;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface MessageQueueDao {
    void handleMessage(String topic, String token, UserBehaviorList actionlist);
}
