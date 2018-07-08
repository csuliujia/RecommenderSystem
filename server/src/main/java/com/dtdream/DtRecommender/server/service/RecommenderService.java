package com.dtdream.DtRecommender.server.service;

import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface RecommenderService {

    RecommenderItems getRecommend(String biz_code, String scn_code, String id);

}
