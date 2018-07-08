package com.dtdream.DtRecommender.server.dao.repository;

import com.dtdream.DtRecommender.server.entity.UserMetaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by handou on 9/26/16.
 */
public interface UserMetaEntityRepository extends CrudRepository<UserMetaEntity, Long> {
    /* 批量插入数据 */
    //int saveBatch();
    <S extends UserMetaEntity> Iterable<S> save(Iterable<S> entities);

    /* 批量获取 */
    List<UserMetaEntity> findAll();
}