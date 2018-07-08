package com.dtdream.DtRecommender.cfgMgr.impl.biz;

import com.dtdream.DtRecommender.cfgMgr.Business;
import com.dtdream.DtRecommender.cfgMgr.BusinessCfg;
import com.dtdream.DtRecommender.cfgMgr.CfgMgrException;
import com.dtdream.DtRecommender.cfgMgr.entity.biz.RBiz;
import com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData.BizCfgData;
import com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData.BizItem;
import com.dtdream.DtRecommender.cfgMgr.sync.CfgUnit;
import com.dtdream.DtRecommender.cfgMgr.sync.SyncCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by handou on 10/11/16.
 * 业务配置的顶层处理类
 *
 */
public class BusinessCfgImpl implements BusinessCfg {
    private BizCfgData bizCfgData = new BizCfgData();
    private final static Logger log = LoggerFactory.getLogger(BusinessCfgImpl.class);
    private SyncCfg syncCfg;
    private CfgUnit rootUnit;

    /* 所有业务的内存数据,biz_code 作为key, 全局一份 */
    private Map<String, RBiz> rBizs_data;


    /* 创建 业务配置 的顶层节点 path: /bizcfg, 其他业务子节点都以此为父节点 */
    public BusinessCfgImpl(SyncCfg syncCfg) {
        this.syncCfg = syncCfg;

        try {
            // 尝试创建根节点
            rootUnit = syncCfg.createRootCfgUnit(BizCfgData.PATH_ROOT_BIZ, bizCfgData);
        } catch (CfgMgrException.ExistException e) {
            try {
                // 根节点已存在，直接获取
                rootUnit = syncCfg.getRootCfgUnit(BizCfgData.PATH_ROOT_BIZ, bizCfgData);
            } catch (CfgMgrException.NotExistException e1) {
                // TODO 基本不可能发生的情况，输出error log
            }
        }
    }

    public Business createBiz(String biz_code, String biz_name, String description) throws CfgMgrException.ExistException, InterruptedException {
        return createBiz(biz_code, biz_name, description, null, null, null, null);
    }

    public Business createBiz(String biz_code, String biz_name, String description, String offline_name,
                              String kvStore_name, String dbRec_name, String mqRec_name) throws CfgMgrException.ExistException, InterruptedException {

        if (null == biz_code || "".equals(biz_code) ||
                null == biz_name || "".equals(biz_name) ||
                null == description || "".equals(description)) {
            throw new InvalidParameterException();
        }

        // 创建业务节点，不需要检查重复，如果重复zookeeper会上报异常。
        BizItem item = new BizItem(biz_code, biz_name, description, offline_name, kvStore_name, dbRec_name, mqRec_name);
        CfgUnit unit = rootUnit.createChildUnit(biz_code, BizCfgData.TYPE_BIZ_ITEM);

        // 等待配置被更细
        bizCfgData.waitUpdate(rootUnit.getPath(), new Object());

        return new BusinessImpl(syncCfg, unit, bizCfgData, biz_code);
    }

    public void deleteBiz(String biz_code) throws InterruptedException {
        if (null == biz_code || "".equals(biz_code)) {
            throw new InvalidParameterException();
        }

        rootUnit.deleteChildUnit(biz_code);

        // 等待配置被更细
        bizCfgData.waitUpdate(rootUnit.getPath(), new Object());

    }

    public Business getBiz(String biz_code) {
        Object o = bizCfgData.getBizMap().get(biz_code).getContext();
        if (null == o) {
            BusinessImpl business = new BusinessImpl(syncCfg, bizCfgData, biz_code);
            bizCfgData.getBizMap().get(biz_code).setContext(business);
            o = business;
        }

        return (Business)o;
    }

    public List<String> getBizList() {
        return new ArrayList<String>(bizCfgData.getBizMap().keySet());
    }



}
