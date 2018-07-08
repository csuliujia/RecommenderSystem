package com.dtdream.DtRecommender.cfgMgr.entity.resource;

import java.io.Serializable;

/**
 *
 */
public class KVStoreRec implements Serializable {
    private String name;
    private String description;
    private String addr;
    private String port;
    private String password;
    private Integer select;

    public KVStoreRec() {
    }

    public KVStoreRec(String name, Integer select, String password, String port, String addr, String description) {
        this.name = name;
        this.select = select;
        this.password = password;
        this.port = port;
        this.addr = addr;
        this.description = description;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSelect() {
        return select;
    }

    public void setSelect(Integer select) {
        this.select = select;
    }
}
