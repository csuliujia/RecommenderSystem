package com.dtdream.DtRecommender.cfgMgr;

import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;

import javax.validation.constraints.NotNull;

/**
 *
 */
public interface PolicyCfg {

    /* 用于控制台----------------------------------------------------------- */

    /**
     * 在内存中创建Policy对象。在ＺooKeeper中创建以name命名的Policy节点。
     * @param name 策略标识，全局唯一
     * @param info 策略描述
     * @return 创建成功，返回一个可编辑的Policy对象；name重复，返回null
     */
    Policy createPolicy(String name, String info);      // 创建策略

    /**
     * @param name 在内存中删除Policy对象。在ZooKeeper中删除节点。
     */
    void deletePolicy(String name);                     // 删除策略

    /**
     * 将内存中的Policy对象同步到ZooKeeper
     */
    void publishPolicy(@NotNull Policy policy);

    /**
     * 获得一个新的Policy用于编辑
     */
    Policy getPolicy(String name);


    /* 用于api server----------------------------------------------------*/
    RecommenderItems runPolicy(String name, String bizCode);

    /**
     * @param name 策略名
     * @return 推荐列表
     */
    RecommenderItems runPolicy(String name, String bizCode, String objId);

}
