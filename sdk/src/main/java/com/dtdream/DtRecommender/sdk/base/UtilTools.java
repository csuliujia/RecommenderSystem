package com.dtdream.DtRecommender.sdk.base;

import java.util.HashMap;

/**
 * Created by handou on 8/20/16.
 */
public class UtilTools {

    /* 拼装Restful Url，包含携带的参数 */
    public static String getRestfulUri(String urlPrefix, HashMap<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        boolean firstFlag = true;

        /* 拼装url前缀 */
        sb.append(urlPrefix);

        /* 拼装参数 */
        for (HashMap.Entry<String, Object> entry: params.entrySet()) {

            if (!firstFlag) {
                /* 添加连接符号 */
                sb.append("&");
            }

            sb.append(entry.getKey()).append("=").append(entry.getValue());

            firstFlag = false;
        }

        return sb.toString();
    }
}
