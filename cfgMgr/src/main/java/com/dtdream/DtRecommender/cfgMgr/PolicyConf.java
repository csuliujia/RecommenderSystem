package com.dtdream.DtRecommender.cfgMgr;

/**
 * Created by handou on 10/11/16.
 */
public interface PolicyConf {
    String getPolicy_name();

    /* 只能修改策略的 流量百分比 */
    double getFlow_ratio();
    void setFlow_ratio(double flow_ratio);
}
