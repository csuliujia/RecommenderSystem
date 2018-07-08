package com.dtdream.DtRecommender.bridge.utils.configuration.bean;


import java.util.Properties;

/**
 * describe a message queue consumer task which is corresponding to a table in MaxCompute.
 */
public class Task {
    private String topic;
    private String tag;
    private String table;
    private long recordNum;
    private long commitCycle;
    private Properties mqConsumerProperties = new Properties();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTableName() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    public long getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(long recordNum) {
        this.recordNum = recordNum;
    }

    public long getCommitCycle() {
        return commitCycle;
    }

    public void setCommitCycle(long commitCycle) {
        this.commitCycle = commitCycle;
    }

    public Properties getMqConsumerProperties() {
        return mqConsumerProperties;
    }

    public void setMqConsumerProperties(String key, String value) {
        this.mqConsumerProperties.setProperty(key, value);
    }
}
