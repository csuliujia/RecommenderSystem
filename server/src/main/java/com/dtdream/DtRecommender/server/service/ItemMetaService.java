package com.dtdream.DtRecommender.server.service;

import com.dtdream.DtRecommender.common.model.item.ItemMeta;
import com.dtdream.DtRecommender.common.model.item.ItemMetaList;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public interface ItemMetaService {
     String postItemConf(ItemMetaList itemMetaList,String biz_code,String token);

     List<ItemMeta> getItemConf(String biz_code,String token);
}
