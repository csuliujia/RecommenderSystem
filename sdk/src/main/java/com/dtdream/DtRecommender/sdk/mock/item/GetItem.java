package com.dtdream.DtRecommender.sdk.mock.item;

import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.model.item.ItemList;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class GetItem {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<String> l = new LinkedList<String>();

        l.add("item001");
        l.add("item002");
        l.add("003");
        l.add("004");
        l.add("005");
        l.add("006");

        ItemList r = ac.getItemInfo(new ItemIdList(l));

        System.out.println("Result: " + JsonHelper.toJsonStr(r));

    }
}
