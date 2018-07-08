package com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData;

import java.io.Serializable;

/**
 * Created by handou on 10/11/16.
 * zookeeper 场景节点存放的data
 *
 */
public class ScnItem implements Serializable {
    private String scn_name;
    private String scn_code;
    private String description;

    public ScnItem() {
    }

    public ScnItem(String scn_name, String scn_code, String description) {
        this.scn_name = scn_name;
        this.scn_code = scn_code;
        this.description = description;
    }

    public String getScn_name() {
        return scn_name;
    }

    public void setScn_name(String scn_name) {
        this.scn_name = scn_name;
    }

    public String getScn_code() {
        return scn_code;
    }

    public void setScn_code(String scn_code) {
        this.scn_code = scn_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
