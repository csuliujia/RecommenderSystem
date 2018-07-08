package com.dtdream.DtRecommender.cfgMgr.entity.resource;

import java.io.Serializable;

/**
 * Created by handou on 10/10/16.
 * 离线计算资源对象
 *
 */
public class OfflineRec implements Serializable {
    private String name;
    private String description;
    private String project_name;
    private String end_point;
    private String tunnel_endpoint;
    private String access_id;
    private String access_key;

    public OfflineRec(String name, String description, String project_name, String end_point, String tunnel_endpoint, String access_id, String access_key) {
        this.name = name;
        this.description = description;
        this.project_name = project_name;
        this.end_point = end_point;
        this.tunnel_endpoint = tunnel_endpoint;
        this.access_id = access_id;
        this.access_key = access_key;
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

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getEnd_point() {
        return end_point;
    }

    public void setEnd_point(String end_point) {
        this.end_point = end_point;
    }

    public String getTunnel_endpoint() {
        return tunnel_endpoint;
    }

    public void setTunnel_endpoint(String tunnel_endpoint) {
        this.tunnel_endpoint = tunnel_endpoint;
    }

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }
}
