package com.dtdream.DtRecommender.cfgMgr;

import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;

import java.util.Map;

/**
 * 一个用于构建完整策略的接口。
 */
public interface Policy {

    enum NodeType {
        RecStart,           // 每个策略必须从该操作开始，该操作时一个虚节点，不执行任何数据操作。
        RecEnd,             // 每个策略必须以该节点结束，该节点负责生成最终推荐结果。
        GetUserRecList,     // 取离线推荐算法产出的用户推荐列表，入参为用户id和算法名称。
        GetUserDefRecList,  // 取默认用户推荐列表（默认列表中的推荐指数都为0）。
        GetItemRecList,     // 取离线推荐算法产出的物品推荐列表，入参为物品id和算法名称。
        GetItemDefRecList,  // 取默认物品推荐列表（默认列表中的推荐指数都为0）。
        Merge,              // 合并两个推荐列表。
        Distinct,           // 去重推荐列表中的重复记录。
        Sort,               // 排序推荐列表。
        TopN                // 取推荐列表中的前N个推荐项。
    }

    int getStartNodeId();                      // 获取起始节点id

    int getEndNodeId();                        // 获取结束节点id

    int createNode(NodeType type, Map<String, String> params);

    void link(int id1, int id2);  // 连接两个策略节点

    void reset(); //清空原有信息。

    RecommenderItems run(String bizCode);                       // 执行策略，指定业务code用户访问改业务中的KV存储资源

    RecommenderItems run(String bizCode, String objId);         // 执行策略，objId可以是用户或物品的id


}
