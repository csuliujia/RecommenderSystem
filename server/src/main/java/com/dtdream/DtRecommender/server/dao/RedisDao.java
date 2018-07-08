package com.dtdream.DtRecommender.server.dao;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface RedisDao {
    List<String> getValue(String index, String key, String subkey);
}
