package com.dtdream.DtRecommender.server.entity;

/**
 *
 * Created by handou on 9/8/16.
 */
public class RedisConf {
    private String host;
    private int port;
    private String auth;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
