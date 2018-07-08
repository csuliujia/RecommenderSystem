package com.dtdream.DtRecommender.server.dao.repository;

import com.dtdream.DtRecommender.server.entity.ItemMetaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
@Repository("ItemMetaEntityRepository")
public interface ItemMetaEntityRepository extends CrudRepository<ItemMetaEntity, Long> {

    List<ItemMetaEntity> findAll();

    <S extends ItemMetaEntity> Iterable<S> save(Iterable<S> entities);
}
