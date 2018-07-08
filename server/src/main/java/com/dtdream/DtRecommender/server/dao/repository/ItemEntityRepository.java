package com.dtdream.DtRecommender.server.dao.repository;

import com.dtdream.DtRecommender.server.entity.ItemEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public interface ItemEntityRepository extends CrudRepository<ItemEntity, Long> {

    List<ItemEntity> findAllByitemIdIn(Collection<String> ids);

    <S extends ItemEntity> Iterable<S> save(Iterable<S> entities);

    @Modifying
    void deleteByitemIdIn(Collection<String> ids);

}
