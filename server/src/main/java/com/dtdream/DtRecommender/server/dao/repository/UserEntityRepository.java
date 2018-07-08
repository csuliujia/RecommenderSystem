package com.dtdream.DtRecommender.server.dao.repository;

import com.dtdream.DtRecommender.server.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;
import java.util.List;

public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findAllByUserIn(Collection<String> ids);

    <S extends UserEntity> Iterable<S> save(Iterable<S> entities);

    @Modifying
    void deleteByUserIn(Collection<String> ids);
}