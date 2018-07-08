package com.dtdream.DtRecommender.server.service;

import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.server.entity.RecItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public interface EnableRecService {

    String postRecItems(String data);

    String deleteRecItems(String data);

    List<RecItemEntity> getRecItems(ItemIdList ids);

}
