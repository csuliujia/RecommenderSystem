package com.dtdream.DtRecommender.sdk.mock.can_rec_item;

import com.dtdream.DtRecommender.common.model.can_rec_item.RecItemList;
import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class GetRecItem {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<String> l = new LinkedList<String>();

        l.add("item001");
        l.add("item002");

        RecItemList r = ac.getRecItemInfo(new ItemIdList(l));

        System.out.println("Result: " + JsonHelper.toJsonStr(r));
    }

}