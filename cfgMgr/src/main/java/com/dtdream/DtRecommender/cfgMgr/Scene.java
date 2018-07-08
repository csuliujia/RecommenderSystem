package com.dtdream.DtRecommender.cfgMgr;

import java.util.List;

/**
 * Created by handou on 10/11/16.
 */
public interface Scene {
    String getScn_code();

    String getScn_name();
    void setScn_name(String scn_name);

    String getDescription();
    void setDescription(String description);

    /* 策略接口 */
    PolicyConf createPolicy(String name, double flow_ratio);
    void deletePolicy(String name);

    List<String> getPolicyList();
}
