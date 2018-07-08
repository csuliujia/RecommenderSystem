package com.dtdream.DtRecommender.sdk.mock;

import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;
import com.dtdream.DtRecommender.sdk.api.APIContext;

/**
 * Created by Administrator on 2016/9/21.
 */
public class TestGet {
    public static RecommenderItems testGet() {
        //测试-从Server获得数据
        APIContext ac = new APIContext("localhost", 8080, "2", "1");
//          APIContext ac = new APIContext("10.99.3.36", 80, "2", "1");

        RecommenderItems re = ac.getRecommendList("oXxO", "scn_code_0");

//         RecommenderItems re = apiContext.getRecommendList("apple_01", "scn_code_1");

//         RecommenderItems re = apiContext.getRecommendList("","scn_code_2");

//         RecommenderItems re = apiContext.getRecommendList("", "scn_code_3");

        System.out.println(re.getTrace_id());
        System.out.println(re.getRec().get(0).getItem_id());
        System.out.println(re.getRec().get(0).getScore());

        return re;
    }
}
