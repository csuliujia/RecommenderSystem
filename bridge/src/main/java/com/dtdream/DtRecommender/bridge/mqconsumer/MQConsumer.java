package com.dtdream.DtRecommender.bridge.mqconsumer;

import com.aliyun.openservices.ons.api.*;
import com.dtdream.DtRecommender.bridge.odps.Table;
import com.dtdream.DtRecommender.bridge.odps.Writer;
import com.dtdream.DtRecommender.bridge.utils.Deserializer;
import com.dtdream.DtRecommender.bridge.utils.configuration.ConfigParser;
import com.dtdream.DtRecommender.bridge.utils.configuration.bean.Project;
import com.dtdream.DtRecommender.bridge.utils.configuration.bean.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;


public class MQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumer.class);
    private ArrayList<Table> tables;

    public MQConsumer(ArrayList<Table> tables) {
        this.tables = tables;
    }

    /**
     * 每一个ConsumerId只能订阅同一个topic, tag.
     */
    public void subscribe(){
        ArrayList<Project> projects = ConfigParser.getInstance().getProjects();
        int tableIndex = 0;
        for (Project p : projects) {
            for (Task t : p.getTasks()) {
                // 创建MQ消费者
                Consumer consumer = ONSFactory.createConsumer(t.getMqConsumerProperties());

                // 设置订阅信息及监听对象
                consumer.subscribe(t.getTopic(), t.getTag(), new MQListener(tables.get(tableIndex++)));

                // 开始监听订阅消息
                consumer.start();
            }
        }
    }

    // MQ消息监听类
    private static class MQListener implements MessageListener {
        private Table table;

        public MQListener(Table table) {
            this.table = table;
        }

        public Action consume(Message message, ConsumeContext consumeContext) {
            logger.info("Received a message {}", message.getMsgID());
            // 消息解析
            HashMap<String, Object> rowMap = Deserializer.toObject(message.getBody());

            // 获取一个可用的Writer
            Writer writer = table.getFreeWriter();

            try {
                // 写入数据
                writer.write(rowMap);
            } catch (Exception e) {
                logger.error("Failed to write MQ message to ODPS {}, try to consume message next time.", e);

                return Action.ReconsumeLater;
            } finally {
                table.recycleWriter(writer);
            }

            return Action.CommitMessage;
        }
    }

}
