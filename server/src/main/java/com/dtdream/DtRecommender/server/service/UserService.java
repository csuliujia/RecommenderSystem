package com.dtdream.DtRecommender.server.service;

import com.dtdream.DtRecommender.common.model.user.UserIdList;
import com.dtdream.DtRecommender.server.entity.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface UserService {
    String postUser(String data);
    List<UserEntity> getUsers(UserIdList ids);
    String deleteUsers(String data);
}
