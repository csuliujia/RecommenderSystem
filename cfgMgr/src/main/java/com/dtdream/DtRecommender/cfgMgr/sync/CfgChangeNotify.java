package com.dtdream.DtRecommender.cfgMgr.sync;

/**
 * Created by Administrator on 2016/10/18.
 */
public interface CfgChangeNotify {
    void batch(CfgUnit rootUnit);           // 批量同步
    void cfgChildChange(CfgUnit cfgUnit);   // 配置创建
    void cfgDataChange(CfgUnit cfgUnit);    // 配置更新
}
