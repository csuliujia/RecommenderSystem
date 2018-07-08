package com.dtdream.DtRecommender.cfgMgr;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CfgMgrException extends Exception {

    public static class ExistException extends CfgMgrException {

    }

    public static class NotExistException extends CfgMgrException {

    }

    public static class NotEmptyException extends CfgMgrException {

    }
}
