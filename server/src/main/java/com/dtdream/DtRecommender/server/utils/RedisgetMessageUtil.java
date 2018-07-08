package com.dtdream.DtRecommender.server.utils;

import com.dtdream.DtRecommender.common.util.AppContextParser;
import com.dtdream.DtRecommender.server.entity.RedisConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2016/8/19.
 */
public class RedisgetMessageUtil {

    private static JedisPool jedisPool;

    private final static Logger log = LoggerFactory.getLogger(RedisgetMessageUtil.class);

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            RedisConf rc = (RedisConf)AppContextParser.getAppicationConext("server", "server.xml", "redis_conf");

            String ADDR= rc.getHost();
            int PORT= rc.getPort();
            String AUTH = rc.getAuth();

            System.out.format("\r\n\r\n ======>   Addr: %s, port: %d, auth: %s\r\n", ADDR, PORT, AUTH);

            // 指定连接(jedis)在空闲多少秒之后关闭
            int TIMEOUT = 10000;
            JedisPoolConfig config = new JedisPoolConfig();

            // 可用连接实例的最大数目，默认值为8；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxActive(1024);

            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
            config.setMaxIdle(200);

            //当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到等待队列中。
            // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，抛出JedisConnectionException；
            config.setMaxWait(10000);

            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);

            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);

            log.info("Connection to server sucessfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        Jedis resource = null;

        try {
            if (jedisPool != null) {
                resource = jedisPool.getResource();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resource;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void RleaseResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
            jedisPool.destroy();
        }
}
}
