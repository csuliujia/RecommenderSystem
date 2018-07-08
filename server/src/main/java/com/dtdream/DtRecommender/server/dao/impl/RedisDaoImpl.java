package com.dtdream.DtRecommender.server.dao.impl;

import com.dtdream.DtRecommender.server.dao.RedisDao;
import com.dtdream.DtRecommender.server.utils.RedisgetMessageUtil;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by Administrator on 2016/8/18.
 * Dao层只取数据，解析都放在service层
 */
@Repository("RedisDao")
public class RedisDaoImpl implements RedisDao{
    private Jedis jedis = RedisgetMessageUtil.getJedis();

//    public RedisDaoImpl(String trace_id, String count_name) {
//        this.trace_id = trace_id;
//        this.count_name = count_name;
//        this.jedis = RedisgetMessageUtil.getJedis();
//    }

    public List<String> getValue(String index, String key, String subkey) {
        jedis.select(Integer.parseInt(index));

        List<String> list =jedis.hmget(key, subkey);

        //里面仅一条String，如：AUdi_007:0.7;Lexus_001:0.7

        RedisgetMessageUtil.RleaseResource(jedis);
        //若不释放jedis资源，jedis使用量达到MAX_ACTIVE数量后将会不可用

        return list;

    }
}
