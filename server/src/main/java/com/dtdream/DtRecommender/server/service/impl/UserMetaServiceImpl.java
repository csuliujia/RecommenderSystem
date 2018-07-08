package com.dtdream.DtRecommender.server.service.impl;

import com.dtdream.DtRecommender.common.model.user.UserMeta;
import com.dtdream.DtRecommender.common.model.user.UserMetaList;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.server.dao.repository.UserMetaEntityRepository;
import com.dtdream.DtRecommender.server.entity.UserMetaEntity;
import com.dtdream.DtRecommender.server.service.UserMetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handou on 9/26/16.
 */

@Service("UserMetaSrv")
@Transactional
public class UserMetaServiceImpl implements UserMetaService {
    private final static Logger log = LoggerFactory.getLogger(UserMetaServiceImpl.class);

    @Autowired
    private UserMetaEntityRepository repository;

    public String postUserConfig(UserMetaList users, String biz_code, String token) {
        List<UserMetaEntity> el = new ArrayList<>();

        for (UserMeta e : users.getUsres()) {

            el.add(new UserMetaEntity(e.getName(), e.getType().toString()));

        }

        Iterable<UserMetaEntity> result = repository.save(el);
        log.debug("data: {} result: {}", result);

        return StatusCode.SUCCESS;
    }

    public List<UserMetaEntity> getUserConfig(String biz_code, String token) {

        return repository.findAll();

    }
}
