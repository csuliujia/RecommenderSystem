package com.dtdream.DtRecommender.sdk.mock.user;

import com.dtdream.DtRecommender.common.model.user.UserIdList;
import com.dtdream.DtRecommender.common.model.user.UserList;
import com.dtdream.DtRecommender.common.model.user.UserMeta;
import com.dtdream.DtRecommender.common.model.user.UserMetaList;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by handou on 9/26/16.
 */
public class GetUserMeta {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");


        UserMetaList r = ac.getUserMetaInfo();

        System.out.println("Result: " + JsonHelper.toJsonStr(r));

    }
}
