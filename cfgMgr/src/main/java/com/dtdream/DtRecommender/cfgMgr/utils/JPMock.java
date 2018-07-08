package com.dtdream.DtRecommender.cfgMgr.utils;

import redis.clients.jedis.Jedis;

/**
 *
 */
public class JPMock {

    public static Jedis getJedis(String bizCode) {
        return new Jedis("skajdlfj");//TODO
    }

    public static void returnJedis(String bizCode) {

    }
}

