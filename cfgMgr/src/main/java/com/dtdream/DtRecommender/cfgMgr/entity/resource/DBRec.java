package com.dtdream.DtRecommender.cfgMgr.entity.resource;

import java.io.Serializable;

/**
 *
 */
public class DBRec implements Serializable {
    private String name;
    private String description;
    private String addr;
    private String port;
    private String username;
    private String password;
    private String database;

    public DBRec() {
    }

    public DBRec(String name, String description, String addr, String port, String username, String password, String database) {
        this.name = name;
        this.description = description;
        this.addr = addr;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
