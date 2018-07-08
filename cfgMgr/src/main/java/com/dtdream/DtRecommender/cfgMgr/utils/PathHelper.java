package com.dtdream.DtRecommender.cfgMgr.utils;

/**
 * Created by handou on 10/11/16.
 */
public class PathHelper {

    public static String getPath(String root, String... nodes) {
        String path = root;

        for (String n : nodes) {
            path += "/" + n;
        }

        return path;
    }

}

