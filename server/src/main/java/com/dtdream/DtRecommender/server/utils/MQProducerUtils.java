package com.dtdream.DtRecommender.server.utils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.dtdream.DtRecommender.common.util.AppContextParser;

/**
 * Created by yzt on 16-8-18.
 */
public class MQProducerUtils {
    private static MQProducerUtils init;
    private Producer producer;

    private MQProducerUtils() {
        this.producer = (Producer)AppContextParser.getAppicationConext("server", "server.xml", "producer");

        producer.start();
    }

    public static MQProducerUtils getInit() {
        if (null == init)
            init = new MQProducerUtils();
        return init;
    }

    public SendResult send(Message msg) {
        return producer.send(msg);
    }
}
