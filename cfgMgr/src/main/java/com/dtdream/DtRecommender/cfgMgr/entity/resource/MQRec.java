package com.dtdream.DtRecommender.cfgMgr.entity.resource;

import java.io.Serializable;

/**
 *
 */
public class MQRec implements Serializable {
    private String name;
    private String description;
    private String accessKey;
    private String secretKey;
    private String projectName;
    private String producerId;
    private String consumerId;
    private Integer consumeThreadNums;
    private String topic;
    private String tag;
    private String table;
    private Long recordNum;
    private Long commitCycle;

    public MQRec() {
    }

    public MQRec(String name, Long commitCycle, Long recordNum, String tag, String table, String topic, Integer consumeThreadNums, String consumerId, String producerId, String projectName, String secretKey, String description, String accessKey) {
        this.name = name;
        this.commitCycle = commitCycle;
        this.recordNum = recordNum;
        this.tag = tag;
        this.table = table;
        this.topic = topic;
        this.consumeThreadNums = consumeThreadNums;
        this.consumerId = consumerId;
        this.producerId = producerId;
        this.projectName = projectName;
        this.secretKey = secretKey;
        this.description = description;
        this.accessKey = accessKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getConsumeThreadNums() {
        return consumeThreadNums;
    }

    public void setConsumeThreadNums(Integer consumeThreadNums) {
        this.consumeThreadNums = consumeThreadNums;
    }

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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Long getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Long recordNum) {
        this.recordNum = recordNum;
    }

    public Long getCommitCycle() {
        return commitCycle;
    }

    public void setCommitCycle(Long commitCycle) {
        this.commitCycle = commitCycle;
    }
}
