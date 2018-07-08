package com.dtdream.DtRecommender.cfgMgr.impl.policy.node;

import com.dtdream.DtRecommender.cfgMgr.utils.JPMock;
import com.dtdream.DtRecommender.common.model.recommender.ItemModel;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * 获得物品推荐列表
 */
public class GetItemRecList extends AbstractNode {
    private static final String REDIS_KEY = "item_rec";
    public GetItemRecList(int id, Map<String, String> params) {
        super(id, params);
    }

    public List<ItemModel> run(String bizCode, String objId, List<List<ItemModel>> inputRecList) {
        Jedis jedis = JPMock.getJedis(bizCode);
        //如何选择数据库index jedis.select(Integer.parseInt(index));
        List<String> strings = jedis.hmget(REDIS_KEY, objId);
        JPMock.returnJedis(bizCode);
        return ItemModel.getItemList(strings);
    }


}
