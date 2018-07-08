package com.dtdream.DtRecommender.server.service.impl;

import com.dtdream.DtRecommender.common.model.can_rec_item.RecItem;
import com.dtdream.DtRecommender.common.model.can_rec_item.RecItemList;
import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.dao.repository.RecItemRepository;
import com.dtdream.DtRecommender.server.entity.RecItemEntity;
import com.dtdream.DtRecommender.server.service.EnableRecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
@Service("EnableRecService")
@Transactional
public class EnableRecServiceImpl implements EnableRecService {
    private final static Logger log = LoggerFactory.getLogger(EnableRecServiceImpl.class);

    @Autowired
    private RecItemRepository recItemRepository;

    @Override
    public String postRecItems(String rec_items){
        RecItemList ril = JsonHelper.fromJsonStr(rec_items,RecItemList.class);
        List<RecItemEntity> recItemList = new ArrayList<>();

        for(RecItem rec_item : ril.getRec_items()) {
            RecItemEntity recItemEntity = new RecItemEntity(rec_item.getItem_id(),rec_item.getItem_info());

            recItemList.add(recItemEntity);
        }

        Iterable<RecItemEntity> result = recItemRepository.save(recItemList);
        log.debug("data: {} result: {}", result);

        return StatusCode.SUCCESS;
    }

    @Override
    public String deleteRecItems(String ids) {
        ItemIdList il = JsonHelper.fromJsonStr(ids, ItemIdList.class);

        recItemRepository.deleteByitemIdIn(il.getItems());
        return StatusCode.SUCCESS;
    }

    @Override
    public List<RecItemEntity> getRecItems(ItemIdList ids) {
        List<RecItemEntity> rl = recItemRepository.findAllByitemIdIn(ids.getItems());

        return rl;
    }


}
