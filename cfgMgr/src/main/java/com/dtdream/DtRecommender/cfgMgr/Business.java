package com.dtdream.DtRecommender.cfgMgr;

import com.dtdream.DtRecommender.cfgMgr.entity.biz.RScene;

import java.util.List;

/**
 * Created by handou on 10/11/16.
 */
public interface Business {

    String getBiz_code();

    String getBiz_name();
    void setBiz_name(String biz_name) throws CfgMgrException.NotExistException;

    String getDescription();
    void setDescription(String description);

    String getOffline_name();
    void setOffline_name(String offline_name);

    String getKvStore_name();
    void setKvStore_name(String kvStore_name);

    String getDbRec_name();
    void setDbRec_name(String dbRec_name);

    String getMqRec_name();
    void setMqRec_name(String mqRec_name);

    /* 添加设置全部可选项的接口 */
    void setAll(String offline_name, String kvStore_name, String dbRec_name, String mqRec_name);


    /* 处理场景接口 */
    Scene createScn(String scn_code, String scn_name, String description);
    void deleteScn(String scn_code);

    RScene getScn(String scn_code);
    List<String> getScnList();
}
