package com.dtdream.DtRecommender.sdk.mock.user;

import com.dtdream.DtRecommender.common.model.user.User;
import com.dtdream.DtRecommender.common.model.user.UserList;
import com.dtdream.DtRecommender.common.restful.OperationMethod;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by handou on 9/22/16.
 */
public class PostUser {

    public static void shoot() {

        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");

        List<User> uList = new LinkedList<User>();

        User u1 = new User("gj001", "name:youyou");
        User u2 = new User("gj002", "name:luobo");
        User u3 = new User("001", "name:satan");
        User u4 = new User("002", "name:zail");
        uList.add(u1);
        uList.add(u2);
        uList.add(u3);
        uList.add(u4);

        ResponseMsg r = ac.updateUser(OperationMethod.NEW, new UserList(uList));

        System.out.println("NEW Result: " + JsonHelper.toJsonStr(r));

    }
}
