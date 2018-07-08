package com.dtdream.DtRecommender.sdk.mock.user;

import com.dtdream.DtRecommender.common.model.user.UserIdList;
import com.dtdream.DtRecommender.common.model.user.UserList;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by handou on 9/23/16.
 */
public class GetUser {
    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<String> l = new LinkedList<String>();

        l.add("001");
        l.add("002");
        l.add("003");
        l.add("004");
        l.add("005");
        l.add("006");

        UserList r = ac.getUserInfo(new UserIdList(l));

        System.out.println("Result: " + JsonHelper.toJsonStr(r));

    }

}
