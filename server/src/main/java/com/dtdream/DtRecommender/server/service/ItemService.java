package com.dtdream.DtRecommender.server.service;

import com.dtdream.DtRecommender.common.model.item.Item;
import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.server.entity.ItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface ItemService {

    String postItem(String data);

    String deleteItems(String data);

    List<ItemEntity> getItems(ItemIdList ids);
}
