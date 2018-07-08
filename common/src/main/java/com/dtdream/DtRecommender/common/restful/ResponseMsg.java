package com.dtdream.DtRecommender.common.restful;

import java.io.Serializable;

/**
 * Created by handou on 9/20/16.
 * 定义HTTP get/post 处理结果格式
 *
 * 调用API成功
 * {
 *     "code":"SUCCESS",
 *     "data":'具体的数据，没有数据填写[success]'
 *     "message":null
 * }
 * 调用API失败
 * {
 *     "code":"${ERROR_CODE}",
 *     "data": null
 *     "message":具体的错误提示信息
 * }
 *
 *  code为SUCCESS，
 *
 */
public class ResponseMsg implements Serializable {
    private String code;
    private String data;
    private String errMsg;

    public ResponseMsg() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
