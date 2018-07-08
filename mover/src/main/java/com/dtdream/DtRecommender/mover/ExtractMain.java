package com.dtdream.DtRecommender.mover;

import com.dtdream.DtRecommender.common.util.LogHelper;
import com.dtdream.DtRecommender.mover.odps.OdpsReader;
import com.dtdream.DtRecommender.mover.redis.RedisWriter;
import com.dtdream.DtRecommender.mover.utils.KVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by handou on 8/24/16.
 *
 * 推荐数据抽取模块：
 * 1、将数据从ODPS中读取，写入到redis KV数据库
 * 2、在Base上能够按天进行增量调度
 * 3、配置文件适配
 * 4、支持多线程读、写操作（暂不考虑）
 * 5、log信息需要添加
 *
 */
public class ExtractMain {

    public static void main(String[] args) {
        LogHelper.configLogPath("mover");

        Logger log = LoggerFactory.getLogger(ExtractMain.class);

        if (args.length != 4) {
            log.error("Usage: java -jar mover.jar <project_name> <table_name> <partition_value> <redis_DBindex>");

            System.exit(2);
        }

        // 创建Writer对象
        Writer w = new RedisWriter(args[3]);

        // 创建Reader对象
        Reader r= new OdpsReader(args[0], args[1], args[2]);

        while (r.next()) {
            KVRecord record = new KVRecord();

            r.read(record);
            w.write(record);
        }

        r.close();
        w.close();
    }
}
