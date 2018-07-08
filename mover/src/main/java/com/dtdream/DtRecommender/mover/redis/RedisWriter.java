package com.dtdream.DtRecommender.mover.redis;

import com.dtdream.DtRecommender.common.util.ConfParser;
import com.dtdream.DtRecommender.mover.Writer;
import com.dtdream.DtRecommender.mover.utils.KVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;


/**
 * Created by handou on 8/24/16.
 */
public class RedisWriter implements Writer {
    private Jedis jReader;
    private final static Logger log = LoggerFactory.getLogger(RedisWriter.class);

    public RedisWriter(String db_index) {
        int index = Integer.parseInt(db_index);

        ConfParser paser = new ConfParser("mover", "redis.conf");
        jReader = new Jedis(paser.getString("host"), paser.getInt("port"));
        jReader.auth(paser.getString("auth"));

        if ((index < 0 ) || (index > Integer.parseInt(jReader.configGet("databases").get(1)))) {
            log.error("Invalid redis database index: " + db_index);
        } else {
            jReader.select(index); /* db index 是直接设置的，和odps的表数据直接映射 */
        }
    }

    public void write(KVRecord record) {
        /* 插入数据 */
        log.debug("key: {}   value: {}", record.getKey(), record.getValue());

        jReader.hmset(record.getKey(), record.getValue());
    }

    public void close() {
        if (jReader != null) {
            jReader.close();
        }
    }
}