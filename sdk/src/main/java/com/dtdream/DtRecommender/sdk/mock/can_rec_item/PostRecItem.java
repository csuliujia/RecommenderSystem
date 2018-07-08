package com.dtdream.DtRecommender.sdk.mock.can_rec_item;

import com.dtdream.DtRecommender.common.model.can_rec_item.RecItem;
import com.dtdream.DtRecommender.common.model.can_rec_item.RecItemList;
import com.dtdream.DtRecommender.common.model.item.Item;
import com.dtdream.DtRecommender.common.model.item.ItemList;
import com.dtdream.DtRecommender.common.restful.OperationMethod;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class PostRecItem {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<RecItem> list1 = new LinkedList<RecItem>();

        RecItem ri = new RecItem("item001","info_001");
        RecItem ri1 = new RecItem("item002","info_002");

        list1.add(ri);
        list1.add(ri1);

        ResponseMsg r = ac.updateEnableRecommenderItems(OperationMethod.NEW, new RecItemList(list1));

        System.out.println("NEW Result: " + JsonHelper.toJsonStr(r));

    }
}
