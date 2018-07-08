package com.dtdream.DtRecommender.server.service.impl;

import com.dtdream.DtRecommender.common.model.item.ItemMeta;
import com.dtdream.DtRecommender.common.model.item.ItemMetaList;
import com.dtdream.DtRecommender.common.model.meta.EntityAttributeType;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.server.dao.repository.ItemMetaEntityRepository;
import com.dtdream.DtRecommender.server.entity.ItemMetaEntity;
import com.dtdream.DtRecommender.server.service.ItemMetaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
@Service("ItemMetaService")
@Transactional
/* 声明这个service所有方法需要事务管理。每一个业务方法开始时都会打开一个事务 */
public class ItemMetaServiceImpl implements ItemMetaService{

    @Resource(name = "ItemMetaEntityRepository")
    private ItemMetaEntityRepository itemMetaEntityRepository;
    @Override
    public String postItemConf(ItemMetaList itemMetaList, String biz_code, String token) {
        List<ItemMetaEntity> el = new ArrayList<>();

        for(ItemMeta itemMeta : itemMetaList.getItems()){

             el.add(new ItemMetaEntity(itemMeta.getName(),itemMeta.getType().toString()));
        }

        itemMetaEntityRepository.save(el);

        return StatusCode.SUCCESS;
    }

    @Override
    public List<ItemMeta> getItemConf(String biz_code, String token) {
        List<ItemMetaEntity> list = itemMetaEntityRepository.findAll();
        List<ItemMeta> listim = new LinkedList<>();

        for(ItemMetaEntity ie : list){
            listim.add(new ItemMeta(ie.getName(), EntityAttributeType.valueOf(ie.getValue())));
        }

        return listim;
    }
}
