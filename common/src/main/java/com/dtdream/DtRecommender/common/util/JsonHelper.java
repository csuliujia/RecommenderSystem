package com.dtdream.DtRecommender.common.util;

import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by Administrator on 2016/9/20.
 */
public class JsonHelper {

    public static String toJsonStr(Object o) {
        ObjectMapper om = new ObjectMapper();
        String js = null;

        try {
            js = om.writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return js;
    }

    public static <T> T fromJsonStr(String json, Class<T> mapClazz) {

        return new Gson().fromJson(json, mapClazz);
    }
}
