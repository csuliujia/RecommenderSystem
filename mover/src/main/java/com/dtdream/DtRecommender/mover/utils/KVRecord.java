package com.dtdream.DtRecommender.mover.utils;

import java.util.Map;

/**
 * Created by handou on 8/24/16.
 */
public class KVRecord {
    private String key; /* 算法名称，trace_id,表名 */
    private Map<String, String> value;

    public KVRecord() {
    }

    public KVRecord(String key, Map<String, String> value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getValue() {
        return value;
    }

    public void setValue(Map<String, String> value) {
        this.value = value;
    }
}
