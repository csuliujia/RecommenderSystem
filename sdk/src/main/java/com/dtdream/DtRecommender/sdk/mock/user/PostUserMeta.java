package com.dtdream.DtRecommender.sdk.mock.user;

import com.dtdream.DtRecommender.common.model.meta.EntityAttributeType;
import com.dtdream.DtRecommender.common.model.user.UserMeta;
import com.dtdream.DtRecommender.common.model.user.UserMetaList;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by handou on 9/26/16.
 */
public class PostUserMeta {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<UserMeta> uList = new LinkedList<UserMeta>();

        UserMeta u1 = new UserMeta("class_prefer", EntityAttributeType.KV_NUM);
        UserMeta u2 = new UserMeta("tags", EntityAttributeType.MV_ENUM);
        UserMeta u3 = new UserMeta("sex", EntityAttributeType.SV_ENUM);
        UserMeta u4 = new UserMeta("age", EntityAttributeType.SV_NUM);
        uList.add(u1);
        uList.add(u2);
        uList.add(u3);
        uList.add(u4);

        ResponseMsg r = ac.postUserConf(new UserMetaList(uList));

        System.out.println("NEW Result: " + JsonHelper.toJsonStr(r));

    }
}
