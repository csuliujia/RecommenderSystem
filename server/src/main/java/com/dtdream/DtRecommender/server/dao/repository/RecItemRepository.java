package com.dtdream.DtRecommender.server.dao.repository;

import com.dtdream.DtRecommender.server.entity.RecItemEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public interface RecItemRepository extends CrudRepository<RecItemEntity, Long> {

    <S extends RecItemEntity> Iterable<S> save(Iterable<S> entities);

    @Modifying
    void deleteByitemIdIn(Collection<String> ids);

    List<RecItemEntity> findAllByitemIdIn(Collection<String> ids);

}
