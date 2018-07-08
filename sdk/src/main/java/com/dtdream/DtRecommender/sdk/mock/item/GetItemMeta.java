package com.dtdream.DtRecommender.sdk.mock.item;

import com.dtdream.DtRecommender.common.model.item.ItemMetaList;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

/**
 * Created by Administrator on 2016/9/27.
 */
public class GetItemMeta {

    public static void shoot(){
        APIContext ac = new APIContext("localhost",8080,"dtdream","1");

        ItemMetaList itemMetaList = ac.getItemConf();

        System.out.println(" Get Message : " + JsonHelper.toJsonStr(itemMetaList));

    }
}
