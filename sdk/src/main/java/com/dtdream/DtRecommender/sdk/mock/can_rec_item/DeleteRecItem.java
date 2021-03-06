package com.dtdream.DtRecommender.sdk.mock.can_rec_item;

import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.restful.OperationMethod;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class DeleteRecItem {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<String> l = new LinkedList<String>();

        l.add("item001");
        l.add("it002");
        l.add("it003");

        ResponseMsg r = ac.updateEnableRecommenderItems(OperationMethod.DELETE, new ItemIdList(l));

        System.out.println("DELETE Result: " + JsonHelper.toJsonStr(r));

    }
}