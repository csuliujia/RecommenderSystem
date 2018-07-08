package com.dtdream.DtRecommender.server.service.impl;

import com.dtdream.DtRecommender.common.model.user.UserIdList;
import com.dtdream.DtRecommender.common.model.user.User;
import com.dtdream.DtRecommender.common.model.user.UserList;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.dao.repository.UserEntityRepository;
import com.dtdream.DtRecommender.server.entity.UserEntity;
import com.dtdream.DtRecommender.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserEntityRepository userRepository;

    @Override
    public List<UserEntity> getUsers(UserIdList ids) {

        return userRepository.findAllByUserIn(ids.getUsers());
    }

    @Override
    public String postUser(String users) {
        UserList ul = JsonHelper.fromJsonStr(users, UserList.class);

        List<UserEntity> el = new ArrayList<>();

        for (User one : ul.getUsers()) {
            UserEntity row = new UserEntity(one.getUser_id(), one.getTags());
            el.add(row);
        }

        Iterable<UserEntity> result = userRepository.save(el);

        for (UserEntity e : result) {
            System.out.println(e);
        }

        return StatusCode.SUCCESS;
    }

    @Override
    public String deleteUsers(String ids) {
        UserIdList il = JsonHelper.fromJsonStr(ids, UserIdList.class);

        userRepository.deleteByUserIn(il.getUsers());

        return StatusCode.SUCCESS;
    }

}