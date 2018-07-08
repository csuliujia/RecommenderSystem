package com.dtdream.DtRecommender.sdk.mock.item;

import com.dtdream.DtRecommender.common.model.item.ItemMeta;
import com.dtdream.DtRecommender.common.model.item.ItemMetaList;
import com.dtdream.DtRecommender.common.model.meta.EntityAttributeType;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/27.
 */
public class PostItemMeta {

    public static void shoot(){
        APIContext ac = new APIContext("localhost",8080,"dtdream","1");
        List<ItemMeta> list = new LinkedList<ItemMeta>();

        ItemMeta im = new ItemMeta("Brand", EntityAttributeType.SV_ENUM);
        ItemMeta im1 = new ItemMeta("Price",EntityAttributeType.SV_NUM);
        ItemMeta im2 = new ItemMeta("comment",EntityAttributeType.KV_NUM);

        list.add(im);
        list.add(im1);
        list.add(im2);

        ItemMetaList iml = new ItemMetaList(list);
        ResponseMsg responseMsg = ac.postItemConf(iml);

        System.out.println(" Post Message : " + JsonHelper.toJsonStr(responseMsg));
    }

}
