package com.dtdream.DtRecommender.server.service;


import com.dtdream.DtRecommender.common.model.userbehavior.UserBehaviorList;

public interface UserBehaviorService {

    String postAction(UserBehaviorList actionlist, String biz_code, String token);
}
