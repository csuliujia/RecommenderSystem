package com.dtdream.DtRecommender.server.dao.impl;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.dtdream.DtRecommender.common.model.userbehavior.UserBehavior;
import com.dtdream.DtRecommender.common.model.userbehavior.UserBehaviorList;
import com.dtdream.DtRecommender.server.dao.MessageQueueDao;
import com.dtdream.DtRecommender.server.utils.MQProducerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/18.
 */
@Repository("MessageQueueDao")
public class MessageQueueDaoImpl implements MessageQueueDao {
    private final static Logger log = LoggerFactory.getLogger(MessageQueueDaoImpl.class);

    /**
     *
     * @param topic
     * @param token
     * @param actionlist
     */
    @Override
    public void handleMessage(String topic, String token, UserBehaviorList actionlist) {

        for (UserBehavior action : actionlist.getActions()) {
            send(topic, token, action);
        }

    }

    private void send(String topic, String token, UserBehavior action) {

        HashMap<String, Object> hashMap = createMsg(action);

        ByteArrayOutputStream bodyStream = new ByteArrayOutputStream();
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(bodyStream);
            out.writeObject(hashMap);
            Message msg = new Message(topic, "action", bodyStream.toByteArray());
            msg.putUserProperties("token", token);

            SendResult result = MQProducerUtils.getInit().send(msg);
            if (result == null) {
                log.debug("Failed to send, message: {}", msg.toString());

            } else {
                log.info("send success {}", result.getMessageId());
            }
        } catch (IOException e) {
            log.error("Exception to send message to MQ, info: {}", e);

            e.printStackTrace();
        }
    }

    private HashMap<String, Object> createMsg(UserBehavior action) {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("user_id", action.getUser_id());
        hashMap.put("item_id", action.getItem_id());
        hashMap.put("bhv_type", action.getBhv_type().toString());
        hashMap.put("bhv_amt", action.getBhv_amt());
        hashMap.put("bhv_cnt", action.getBhv_cnt());
        hashMap.put("bhv_datetime", action.getBhv_datetime());
        hashMap.put("content", action.getContent());
        hashMap.put("media_type", action.getMedia_type().toString());
        hashMap.put("pos_type", action.getPos_type().toString());
        hashMap.put("position", action.getPosition());
        hashMap.put("env", action.getEnv());
        hashMap.put("trace_id", action.getTrace_id());

        return hashMap;
    }
}
