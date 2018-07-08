package com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData;

import java.io.Serializable;

/**
 * Created by handou on 10/10/16.
 * 策略节点存放的data
 *
 *
 */
public class PolicyItem implements Serializable {
    private String policy_name;
    private double flow_ratio;  /* 该策略占的流量比,给场景设置策略的时候，需要指定 */

    public PolicyItem(String policy_name, double flow_ratio) {
        this.policy_name = policy_name;
        this.flow_ratio = flow_ratio;
    }


    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public double getFlow_ratio() {
        return flow_ratio;
    }

    public void setFlow_ratio(double flow_ratio) {
        this.flow_ratio = flow_ratio;
    }
}
