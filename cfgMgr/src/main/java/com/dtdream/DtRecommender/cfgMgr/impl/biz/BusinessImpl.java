package com.dtdream.DtRecommender.cfgMgr.impl.biz;

import com.dtdream.DtRecommender.cfgMgr.Business;
import com.dtdream.DtRecommender.cfgMgr.BusinessCfg;
import com.dtdream.DtRecommender.cfgMgr.CfgMgrException;
import com.dtdream.DtRecommender.cfgMgr.Scene;
import com.dtdream.DtRecommender.cfgMgr.entity.biz.RScene;
import com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData.BizCfgData;
import com.dtdream.DtRecommender.cfgMgr.sync.CfgUnit;
import com.dtdream.DtRecommender.cfgMgr.sync.SyncCfg;
import com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper;
import com.dtdream.DtRecommender.cfgMgr.utils.PathHelper;
import com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData.BizItem;
import com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.*;

import static com.dtdream.DtRecommender.cfgMgr.zk.ZNodePath.PATH_BIZCFG_ROOT;
import static com.dtdream.DtRecommender.cfgMgr.zk.ZkMethod.deleteGroup;

/**
 * Created by handou on 10/11/16.
 * 业务一级节点 处理的实现类
 *
 */
public class BusinessImpl implements Business {
    private final static Logger log = LoggerFactory.getLogger(BusinessImpl.class);

    /* 所依附的的配置管理实例 */
    private SyncCfg syncCfg;
    private CfgUnit unit;
    private BizCfgData bizCfgData;
    private String biz_code;


    public BusinessImpl(SyncCfg syncCfg, BizCfgData bizCfgData, String biz_code) {
        this.syncCfg = syncCfg;
        this.bizCfgData = bizCfgData;
        this.biz_code = biz_code;
    }

    public BusinessImpl(SyncCfg syncCfg, CfgUnit unit, BizCfgData bizCfgData, String biz_code) {
        this.syncCfg = syncCfg;
        this.unit = unit;
        this.bizCfgData = bizCfgData;
        this.biz_code = biz_code;
    }

    private CfgUnit getUnit() throws CfgMgrException.NotExistException {
        if (null == unit) {
            CfgUnit root = syncCfg.getRootCfgUnit(biz_code, bizCfgData);
            unit = root.getChildUnit(biz_code);
        }

        return unit;
    }

    // 读数据接口

    public String getBiz_code() {

        return biz_code;
    }

    public String getBiz_name() {
        return bizCfgData.getBizMap().get(biz_code).getBiz_data().getBiz_name();
    }

    public String getDescription() {
        return bizCfgData.getBizMap().get(biz_code).getBiz_data().getDescription();
    }

    public String getOffline_name() {
        return bizCfgData.getBizMap().get(biz_code).getBiz_data().getOffline_name();
    }

    public String getKvStore_name() {
        return bizCfgData.getBizMap().get(biz_code).getBiz_data().getKvStore_name();
    }

    public String getDbRec_name() {
        return bizCfgData.getBizMap().get(biz_code).getBiz_data().getDbRec_name();
    }

    public String getMqRec_name() {
        return bizCfgData.getBizMap().get(biz_code).getBiz_data().getMqRec_name();
    }

    // 写数据接口

    private void updateData(BizItem item) throws CfgMgrException.NotExistException {
        CfgUnit unit = getUnit();
        unit.writeCfgData(BytesHelper.toBytes(item));
        try {
            bizCfgData.waitUpdate(unit.getPath(), item);
        } catch (InterruptedException e) {
            // TODO 输出error log
        }

    }

    public void setBiz_name(String biz_name) throws CfgMgrException.NotExistException {
        BizItem item = bizCfgData.getBizMap().get(biz_code).getBiz_data();

        BizItem itemNew = new BizItem();
        BeanUtils.copyProperties(item, itemNew);
        itemNew.setBiz_name(biz_name);

        updateData(itemNew);

        return;
    }



    public void setDescription(String description) {

    }


    public void setOffline_name(String offline_name) {

    }



    public void setKvStore_name(String kvStore_name) {

    }



    public void setDbRec_name(String dbRec_name) {

    }



    public void setMqRec_name(String mqRec_name) {

    }

    public void setAll(String offline_name, String kvStore_name, String dbRec_name, String mqRec_name) {
        setOffline_name(offline_name);
        setKvStore_name(kvStore_name);
        setDbRec_name(dbRec_name);
        setMqRec_name(mqRec_name);
    }

    public Scene createScn(String scn_code, String scn_name, String description) {
        return new SceneImpl(zk, getBiz_code(), scn_code, scn_name, description);
    }

    public void deleteScn(String scn_code) {

        String path = PathHelper.getPath(PATH_BIZCFG_ROOT, item.getBiz_code(), scn_code);
        deleteGroup(zk, path);

        /* 内存数据的更新，在 watch中 进行处理 */
    }

    public RScene getScn(String scn_code) {
        return biz_root.getBiz(getBiz_code()).getScnList().get(scn_code);
    }

    public List<String> getScnList() {
        return new ArrayList<String>(biz_root.getBiz(getBiz_code()).getScnList().keySet());
    }

}
