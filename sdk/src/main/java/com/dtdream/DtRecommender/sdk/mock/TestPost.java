package com.dtdream.DtRecommender.sdk.mock;

import com.dtdream.DtRecommender.common.model.userbehavior.*;
import com.dtdream.DtRecommender.sdk.api.APIContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class TestPost {

    public static void testPostFile() {
        APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");
//        APIContext ac = new APIContext("10.99.3.36", 80, "dtdream", "1");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File("d://u.data");
        BufferedReader reader = null;
        int count = 0;
        int counttotal = 0;
        List<UserBehavior> l = new LinkedList<UserBehavior>();
        UserBehaviorList ll = new UserBehaviorList();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;

            while ((tempString = reader.readLine()) != null) {

                String[] tokens = tempString.split("\\s+");

                Bhv_Type bhv_type = Bhv_Type.CLICK;

                switch (Integer.parseInt(tokens[2])) {
                    case 1:
                        bhv_type = Bhv_Type.CLICK;
                        break;

                    case 2:
                        bhv_type = Bhv_Type.COLLECT;
                        break;

                    case 3:
                        bhv_type = Bhv_Type.COMMENT;
                        break;

                    case 4:
                        bhv_type = Bhv_Type.GRADE;
                        break;

                    case 5:
                        bhv_type = Bhv_Type.SHARE;
                        break;

                    default:
                        System.out.println("Undefined bhv_type");
                        break;
                }

                Long time = new Long(tokens[3]);
                String d = sf.format(time * 1000);
                Date date = sf.parse(d);

                UserBehavior ss = new UserBehavior(tokens[0], tokens[1], bhv_type, date);
                ss.setMedia_type(Media_Type.SINA_WB);
                ss.setPos_type(PositionType.GH);
                l.add(ss);
                count++;
                counttotal++;

                if (count == 3) {

                    ll.setActions(l);
                    ac.uploadUserActionInfo(ll);
                    l.removeAll(l);

                }

                if (count == 5) {

                    ll.setActions(l);
                    ac.uploadUserActionInfo(ll);
                    l.removeAll(l);
                    count = 0;
                }

            }

            if (count != 0) {
                ll.setActions(l);
                ac.uploadUserActionInfo(ll);
            }

            reader.close();
            System.out.println("数据总量========》：" + counttotal);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {
                }
            }
        }
    }


    public static void testPost() {
        APIContext ac = new APIContext("localhost", 8080, "Topic_recommender", "1");
        //     APIContext ac = new APIContext("localhost", 8080, "dtdream", "1");
        UserBehavior a1 = new UserBehavior("abcdabcdabcd", "1007", Bhv_Type.COMMENT, new Date());
        a1.setTrace_id("user_cfr");
        a1.setBhv_amt(0.9);
        a1.setBhv_cnt(1.0);
        a1.setContent("卡迪拉克");
        a1.setEnv("innerl");
        a1.setMedia_type(Media_Type.SINA_WB);
        a1.setPos_type(PositionType.GH);
        a1.setPosition("wrxv");

        UserBehavior a2 = new UserBehavior("abcdabcdabcd", "1008", Bhv_Type.CONSUME, new Date());
        a2.setTrace_id("user_cfr");
        a2.setBhv_amt(0.9);
        a2.setBhv_cnt(1.0);
        a2.setContent("林肯");
        a2.setEnv("innerl");
        a2.setMedia_type(Media_Type.LAIWANG_FRIEND);
        a2.setPos_type(PositionType.GH);
        a2.setPosition("wrxv");

        List<UserBehavior> l = new LinkedList<UserBehavior>();
        l.add(a1);
        l.add(a2);

        UserBehaviorList ll = new UserBehaviorList();
        ll.setActions(l);

        String result = ac.uploadUserActionInfo(ll).getCode();
        System.out.println("result:  " + result);
    }


}
