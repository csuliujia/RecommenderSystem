package com.dtdream.DtRecommender.cfgMgr;

import com.dtdream.DtRecommender.cfgMgr.entity.resource.DBRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.KVStoreRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.MQRec;
import com.dtdream.DtRecommender.cfgMgr.entity.resource.OfflineRec;

import java.util.List;

/**
 *
 * 所有资源以"name"唯一标示
 *
 *
 */
public interface ResourceCfg {

    // 创建离线计算资源
    void createOfflineRec(OfflineRec olRec);
    // 创建KV存储资源
    void createKVStoreRec(KVStoreRec kvsRec);
    // 创建数据库资源
    void createDBRec(DBRec dbRec);
    // 创建MQ资源
    void createMQRec(MQRec dbRec);

    // 更新离线计算资源
    void updateOfflineRec(OfflineRec olRec);
    // 更新KV存储资源
    void updateKVStoreRec(KVStoreRec kvsRec);
    // 更新数据库资源
    void updateDBRec(DBRec dbRec);
    // 更新MQ资源
    void updateMQRec(MQRec dbRec);

    // 删除离线计算资源
    void deleteOfflineRec(String name);
    // 删除KV存储资源
    void deleteKVStoreRec(String name);
    // 删除数据库资源
    void deleteDBRec(String name);
    // 删除MQ资源
    void deleteMQRec(String name);

    // 获取离线计算资源
    OfflineRec getOfflineRec(String name);
    // 获取KV存储资源
    KVStoreRec getKVStoreRec(String name);
    // 获取数据库资源
    DBRec getDBRec(String name);
    // 获取MQ资源
    MQRec getMQRec(String name);

    // 获取离线计算资源列表
    List<OfflineRec> getOfflineRecList(String type);
    // 获取KV存储资源列表
    List<KVStoreRec> getKVStoreRecList(String type);
    // 获取数据库资源列表
    List<DBRec> getDBRecList(String type);
    // 获取MQ列表
    List<MQRec> getMQRecList(String type);
}
