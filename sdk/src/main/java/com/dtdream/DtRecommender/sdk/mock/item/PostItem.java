package com.dtdream.DtRecommender.sdk.mock.item;

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
public class PostItem {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<Item> iList = new LinkedList<Item>();

        Item i1 = new Item("item001","car","truck","description","name:truck_A","bizinfo");
        Item i2 = new Item("item002","car","truck","description","name:truck_B","bizinfo");

        iList.add(i1);
        iList.add(i2);

        ResponseMsg r = ac.updateItem(OperationMethod.NEW, new ItemList(iList));

        System.out.println("NEW Result: " + JsonHelper.toJsonStr(r));

    }
}
