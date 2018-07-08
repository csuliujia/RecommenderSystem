package com.dtdream.DtRecommender.server.service.impl;

import com.dtdream.DtRecommender.common.model.recommender.ItemModel;
import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;
import com.dtdream.DtRecommender.server.entity.ScnCode;
import com.dtdream.DtRecommender.server.service.util.StrategyMethod;
import com.dtdream.DtRecommender.server.service.RecommenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;



/**
 * Created by Administrator on 2016/8/18.
 */
@Service("RecommenderService")
public class RecommenderServiceImpl implements RecommenderService {
    @Resource(name = "strategymethod")
    private StrategyMethod strategy;

    private final static Logger log = LoggerFactory.getLogger(RecommenderServiceImpl.class);

    /* biz_code 与 redis数据库 index的映射，后面这个是后天配置完成的，这里暂写mock代码 */
    private String bizCodeToIndex(String biz_code) {

        return biz_code;
    }

    private ScnCode parseScn(String code) {
        ScnCode scn = ScnCode.UNDEFINED;

        switch (code) {
            case "scn_code_0":
                scn = ScnCode.USER_PREFER;
                break;

            case "scn_code_1":
                scn = ScnCode.ITEM_RELATED;
                break;

            case "scn_code_2":
                scn = ScnCode.HOT_ITEMS;
                break;

            case "scn_code_3":
                scn = ScnCode.NEW_ITEMS;
                break;

            default:
                log.error("Undefined scn code {}", code);
                break;
        }

        return scn;
    }

    public RecommenderItems getRecommend(String biz_code, String scn_code, String id){
        RecommenderItems result = null;

        String index = bizCodeToIndex(biz_code);
        ScnCode scn = parseScn(scn_code);

        switch (scn) {
            case USER_PREFER:
                result = getUserPrefer(index, id);
                break;

            case ITEM_RELATED:
                result = getItemRelated(index, id);
                break;

            case HOT_ITEMS:
                result = getHotItems(index);
                break;

            case NEW_ITEMS:
                result = getNewItems(index);
                break;

            default:
                log.error("invalid scn code {}", scn);
                break;
        }

        return result;
    }


    /* 获取用户喜好的物品列表 */
    private RecommenderItems getUserPrefer(String index, String user_id) {

        //直接写死, 测试数据是从表 user_cfr 中获取结果，是后台推荐策略配置的
        List<ItemModel> user_lst = strategy.getValue(index, "user_cfr", user_id);

        //取统计表里存有热门物品和最新物品
        List<ItemModel> def_lst = strategy.getValue(index, "statistic", "jd_hot");

        List<ItemModel> listmerge = strategy.merge(user_lst, def_lst);
        List<ItemModel> listdistinct = strategy.distinct(listmerge);
        strategy.sort(listdistinct);

        List<ItemModel> result = strategy.getTopN(listdistinct, 5);

        //实际返回的是 推荐策略  的id
        String trace_id = "user_cfr_20160901";

        return new RecommenderItems(trace_id, result);
    }

    /* 获取相关物品列表 */
    public RecommenderItems getItemRelated(String index, String item_id) {

        //直接写死, 从表 item_cfr 中获取结果
        List<ItemModel> user_lst = strategy.getValue(index, "item_cfr", item_id);

        strategy.sort(user_lst);

        List<ItemModel> result = strategy.getTopN(user_lst, 5);

        //实际返回的是 推荐策略  的id
        String trace_id = "item_cfr_20160901";

        return new RecommenderItems(trace_id, result);
    }

    /* 获取热门物品列表 */
    public RecommenderItems getHotItems(String index) {

        List<ItemModel> lst = strategy.getValue(index, "statistic", "fruit_hot");

        return new RecommenderItems("statistics_001", lst);
    }

    /* 获取新品列表 */
    public RecommenderItems getNewItems(String index) {

        List<ItemModel> lst = strategy.getValue(index, "statistic", "jd_new");

        return new RecommenderItems("statistics_002", lst);
    }

    /* 获取推荐的策略：特定业务场景下，推荐的处理流程，包括处理的规则和算法 */
}