package com.dtdream.DtRecommender.server.service.util;

import com.dtdream.DtRecommender.common.model.recommender.ItemModel;
import com.dtdream.DtRecommender.server.dao.RedisDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by handou on 8/30/16.
 *
 * 复杂推荐策略的原子操作：
 * 合并
 * 去重
 * 排序
 * TopN
 * 通过user_id，获取用户的最感兴趣的商品；
 * 通过item_id，获取与该商品相似度最高的N种商品；
 * 过滤（暂不考虑）
 *
 */
@Repository("strategymethod")
public class StrategyMethod {
    @Resource(name = "RedisDao")
    private RedisDao rd;

    /* 对两个list进行合并 */
    public List<ItemModel> merge(List<ItemModel> l1, List<ItemModel> l2) {

        l1.addAll(l2);

        return l1;

    }

    /* 去除重复的Item */
    public List<ItemModel> distinct(List<ItemModel> l1){
        HashSet<ItemModel> hs = new HashSet<ItemModel>(l1);
        List<ItemModel> l = new LinkedList<>(hs);
        return l;
    }

    public void sort(List<ItemModel> l) {

        Comparator comparator = new Comparator(){

            public int compare(Object op1,Object op2){
                ItemModel Item1=(ItemModel)op1;
                ItemModel Item2=(ItemModel)op2;

                // 按评分进行排序
                return Double.compare(Item2.getScore(), Item1.getScore());
            }
        };

        Collections.sort(l, comparator);
    }

    public List<ItemModel> getTopN(List<ItemModel> list, int n) {

        if (list.size() < n) {
            return list;
        } else {
            return list.subList(0, n);
        }
    }


    /* 获取场景对应推荐策略中的推荐算法中，该用户的推荐数据 */
    public List<ItemModel> getValue(String index, String key, String subkey) {
        List<String> ls = rd.getValue(index, key, subkey);

        return ItemModel.getItemList(ls);
    }
}
