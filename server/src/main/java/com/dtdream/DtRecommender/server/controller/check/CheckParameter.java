package com.dtdream.DtRecommender.server.controller.check;

/**
 * Created by Administrator on 2016/9/21.
 */

 /* 检查URL参数  */
public class CheckParameter {

    public static Boolean checkBiz_code(String biz_code) {
        Boolean flag = true;
        if (biz_code.equals("Topic_recommender") || biz_code.equals("dtdream")) {

            return flag;
        } else {

            flag = false;
        }

        return flag;
    }


    public static Boolean checkBiz_code(String biz_code, String scn_code) {
        Boolean flag = true;
        if ((biz_code.equals("2") && !(scn_code.equals("")))) {

            return flag;
        } else {

            flag = false;
        }

        return flag;
    }

    public static Boolean checkScn_code(String scn_code, String id) {
        Boolean flag = true;
        if (scn_code.equals("scn_code_0") || scn_code.equals("scn_code_1")) {
            if (id != null) {

                return flag;
            } else {

                flag = false;
            }

        } else if (scn_code.equals("scn_code_2") || scn_code.equals("scn_code_3")) {

            return flag;
        } else {

            flag = false;
        }

        return flag;
    }

}
