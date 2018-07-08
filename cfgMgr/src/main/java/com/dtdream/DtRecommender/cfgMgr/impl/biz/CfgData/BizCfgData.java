package com.dtdream.DtRecommender.cfgMgr.impl.biz.CfgData;

import com.dtdream.DtRecommender.cfgMgr.CfgMgrException;
import com.dtdream.DtRecommender.cfgMgr.sync.CfgChangeNotify;
import com.dtdream.DtRecommender.cfgMgr.sync.CfgUnit;
import com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/18.
 */
public class BizCfgData implements CfgChangeNotify {
    public static final String PATH_ROOT_BIZ = "bizcfg";
    public static final String PATH_ROOT_BIZ_SCN = "scncfg";
    public static final String PATH_ROOT_BIZ_POLICY = "policycfg";

    public static byte TYPE_BIZ = 1;
    public static byte TYPE_BIZ_ITEM = 2;
    public static byte TYPE_SCN = 3;
    public static byte TYPE_POLICY = 4;
    public static byte TYPE_SCN_ITEM = 5;
    public static byte TYPE_POLICY_ITEM = 6;

    private Map<String, RBiz> bizMap;
    private Map<String, Object> locks;

    public BizCfgData() {
        bizMap = new HashMap<String, RBiz>();
    }

    // 等待配置项更新
    public  synchronized void waitUpdate(String path, Object lock) throws InterruptedException {
        locks.put(path, lock);
        lock.wait();

        return;
    }

    // 通知配置项更新
    public void notifyUpdate(String path) throws InterruptedException {
        Object o = locks.get(path);
        if (null != o) {
            locks.remove(path);
            o.notify();
        }

        return;
    }

    public Map<String, RBiz> getBizMap() {
        return bizMap;
    }

    public synchronized void batch(CfgUnit rootUnit) {

        // 重置内存
        Map<String, RBiz> bizMapTmp = new HashMap<String, RBiz>();

        // 恢复数据
        recoverBiz(rootUnit, bizMapTmp);

        // 解锁所有的等待
        // TODO

        bizMap = bizMapTmp;
    }

    public void cfgChildChange(CfgUnit cfgUnit) {
        // TODO

    }

    public void cfgDataChange(CfgUnit cfgUnit) {
        // 判断更新类型

        // 确定根性目标
        try {
            cfgUnit.getParentUnit().getParentUnit().getName();
        } catch (CfgMgrException.NotExistException e) {
            e.printStackTrace();
        }
        cfgUnit.getName();

        // 更新目标
        byte[] data = cfgUnit.readCfgData();


        // 通知数据更新
        try {
            notifyUpdate(cfgUnit.getPath());
        } catch (InterruptedException e) {
            // TODO 输出错误日志
            e.printStackTrace();
        }
    }

    private void recoverBiz(CfgUnit rootUnit, Map<String, RBiz> bizMap) {

        List<CfgUnit> list = rootUnit.getChildsUnit();
        for (CfgUnit unit : list) {

            if (unit.getType() != TYPE_BIZ) {
                // TODO 输出错误日志
                continue;
            }

            // 恢复业务配置数据
            BizItem item = BytesHelper.fromBytes(unit.readCfgData(), BizItem.class);
            RBiz rBiz = new RBiz(item);
            bizMap.put(unit.getName(), rBiz);

            // 恢复业务子配置
            List<CfgUnit> childUnitList = unit.getChildsUnit();
            for (CfgUnit chUnit : childUnitList) {

                // 恢复场景配置
                if (PATH_ROOT_BIZ_SCN.equals(chUnit.getName())) {
                    recoverScn(unit, rBiz.getScnList());
                }

                // 恢复策略配置
                if (PATH_ROOT_BIZ_POLICY.equals(chUnit.getName())) {
                    recoverPolicy(unit, rBiz.getPolicyList());
                }
            }



        }

    }

    private void recoverScn(CfgUnit cfgUnit, Map<String, RScene> scnMap) {
        // TODO
    }

    private void recoverPolicy(CfgUnit cfgUnit, Map<String, RPolicy> policyMap) {
        // TODO
    }


    public static class RBiz {
        private BizItem biz_data;
        private Object context;
        private Map<String, RScene> scnList;    /* 业务节点下的所有场景子节点 */
        private Map<String, RPolicy> policyList;    /* 业务节点下的所有场景子节点 */

        public RBiz(BizItem biz_data) {
            this.biz_data = biz_data;
            this.scnList = new HashMap<String, RScene>();
            this.policyList = new HashMap<String, RPolicy>();
        }

        public Object getContext() {
            return context;
        }

        public void setContext(Object context) {
            this.context = context;
        }

        public BizItem getBiz_data() {
            return biz_data;
        }

        public void setBiz_data(BizItem biz_data) {
            this.biz_data = biz_data;
        }

        public Map<String, RScene> getScnList() {
            return scnList;
        }

        public void setScnList(Map<String, RScene> scnList) {
            this.scnList = scnList;
        }

        public Map<String, RPolicy> getPolicyList() {
            return policyList;
        }

        public void setPolicyList(Map<String, RPolicy> policyList) {
            this.policyList = policyList;
        }
    }

    public static class RScene {
        private ScnItem scn_data;
        private Map<String, PolicyItem> policyItems;   /* 场景节点下的所有场景子节点 */

        public RScene(ScnItem scn_data) {
            this.scn_data = scn_data;
            this.policyItems = new HashMap<String, PolicyItem>();
        }

        public ScnItem getScn_data() {
            return scn_data;
        }

        public void setScn_data(ScnItem scn_data) {
            this.scn_data = scn_data;
        }

        public Map<String, PolicyItem> getPolicyItems() {
            return policyItems;
        }

        public void setPolicyItems(Map<String, PolicyItem> policyItems) {
            this.policyItems = policyItems;
        }
    }

    public static class RPolicy {

    }


}
