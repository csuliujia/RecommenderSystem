package com.dtdream.DtRecommender.server.service;

import com.dtdream.DtRecommender.common.model.user.UserMetaList;
import com.dtdream.DtRecommender.server.entity.UserMetaEntity;

import java.util.List;

/**
 * Created by handou on 9/26/16.
 */
public interface UserMetaService {
    /* user mete interface */
    String postUserConfig(UserMetaList users, String biz_code, String token);
    List<UserMetaEntity> getUserConfig(String biz_code, String token);
}
