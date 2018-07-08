package com.dtdream.DtRecommender.server.service.impl;

import com.dtdream.DtRecommender.common.model.item.Item;
import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.model.item.ItemList;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.dao.repository.ItemEntityRepository;
import com.dtdream.DtRecommender.server.entity.ItemEntity;
import com.dtdream.DtRecommender.server.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */

@Service("ItemService")
@Transactional
public class ItemServiceImpl implements ItemService {
    private final static Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemEntityRepository itemRepository;

    @Override
    public String postItem(String items) {
        ItemList il = JsonHelper.fromJsonStr(items, ItemList.class);
        List<ItemEntity> el = new ArrayList<>();

        for (Item one : il.getItems()) {
            ItemEntity row = new ItemEntity(one.getItem_id(), one.getCategory(),one.getKeywords(),
                    one.getDescription(),one.getProperties(),one.getBizinfo());

            el.add(row);

        }

        Iterable<ItemEntity> result = itemRepository.save(el);
        log.debug("data: {} result: {}", result);

        return StatusCode.SUCCESS;
    }

    @Override
    public String deleteItems(String ids) {
        ItemIdList il = JsonHelper.fromJsonStr(ids, ItemIdList.class);

        itemRepository.deleteByitemIdIn(il.getItems());
        return StatusCode.SUCCESS;
    }

    @Override
    public List<ItemEntity> getItems(ItemIdList ids) {
        List<ItemEntity> lst =  itemRepository.findAllByitemIdIn(ids.getItems());

        return lst;
    }

}
