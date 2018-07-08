package com.dtdream.DtRecommender.cfgMgr;

import com.dtdream.DtRecommender.cfgMgr.entity.biz.RBiz;

import java.util.List;

/**
 * Created by handou on 10/11/16.
 */
public interface BusinessCfg {
    Business createBiz(String biz_code, String biz_name, String description) throws CfgMgrException.ExistException, InterruptedException;
    Business createBiz(String biz_code, String biz_name, String description, String offline_name,
                       String kvStore_name, String dbRec_name, String mqRec_name) throws CfgMgrException.ExistException, InterruptedException;
    void deleteBiz(String biz_code) throws InterruptedException;

    /* 查询接口 */
    Business getBiz(String biz_code);
    List<String> getBizList();
}
