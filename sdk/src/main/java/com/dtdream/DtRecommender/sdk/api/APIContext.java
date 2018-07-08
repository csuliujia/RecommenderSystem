package com.dtdream.DtRecommender.sdk.api;

import com.dtdream.DtRecommender.common.model.can_rec_item.RecItemList;
import com.dtdream.DtRecommender.common.model.userbehavior.UserBehaviorList;
import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.model.item.ItemList;
import com.dtdream.DtRecommender.common.model.item.ItemMetaList;
import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;
import com.dtdream.DtRecommender.common.model.user.UserIdList;
import com.dtdream.DtRecommender.common.model.user.UserList;
import com.dtdream.DtRecommender.common.model.user.UserMetaList;
import com.dtdream.DtRecommender.common.restful.OperationMethod;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.common.util.LogHelper;
import com.dtdream.DtRecommender.sdk.base.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static com.dtdream.DtRecommender.sdk.base.HttpMethod.*;
import static com.dtdream.DtRecommender.sdk.base.UtilTools.getRestfulUri;

/**
 * Created by handou on 8/20/16.
 */
public class APIContext {
    private String domainName;  /* 调用的远端域名 */
    private int port;        /* 端口号 */
    private String biz_code;    /* 业务编码 */
    private String token;

    private final static Logger log = LoggerFactory.getLogger(HttpMethod.class);

    /*
    * 外部业务模块全局初始化一次，后面基于该对象进行操作
    * */
    public APIContext(String domainName, int port, String biz_code, String token) {
        this.domainName = domainName;
        this.port = port;
        this.biz_code = biz_code;
        this.token = token;

        LogHelper.configLogPath("sdk");
    }

    /* 获取url地址的前缀 */
    private String getUrlPrefix(String apiPrefix) {

        return String.format("http://%s:%d%s?", this.domainName, this.port, apiPrefix);
    }

    /*
    *           用户行为收集API
    * */


    /**
     *
     * 上传用户行为信息到推荐系统
     *
     * @param acList 用户行为数据
     * @return 处理结果
     */
    public ResponseMsg uploadUserActionInfo(UserBehaviorList acList) {

        /* 获取url(包含参数） */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/action"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(acList);

        /* 返回数据的处理 */
        return doPost(url, data);
    }

    /*
    *           推荐API
    * */

    /* 获取推荐物品列表 */
    public RecommenderItems getRecommendList(String id, String scn_code) {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);
        params.put("scn_code", scn_code);

        if(id != null) {
            params.put("id", id);
        }

        String url = getRestfulUri(getUrlPrefix("/api/v1/recommender"), params);

        ResponseMsg rm = doGet(url);

        if (rm.getCode().equals(StatusCode.FAILURE)) {
            log.error("Failed to get recommender list, error message: {}", rm.getErrMsg());

            return null;
        }

        return JsonHelper.fromJsonStr(rm.getData(), RecommenderItems.class);
    }

    /*
    *           用户API
    * */

    /* 更新用户信息，依据method参数确定具体类型 */
    public ResponseMsg updateUser(OperationMethod method, Object t) {
        /* 获取url(包含参数） */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);
        params.put("method", method.getLabel());

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/user"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(t);

        /* 返回数据的处理 */
        return doPost(url, data);
    }



    /* 获取用户信息 */
    public UserList getUserInfo(UserIdList users) {
        /* 获取url(包含参数） */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/user"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(users);

        ResponseMsg rm = doPut(url, data);

        if (rm.getCode().equals(StatusCode.FAILURE)) {
            log.error("Failed to get user list, error message: {}", rm.getErrMsg());

            return null;
        }

        return JsonHelper.fromJsonStr(rm.getData(), UserList.class);
    }

    /* 上传用户属性的描述信息 */
    public ResponseMsg postUserConf(UserMetaList users) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/userConfig"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(users);

        /* 返回数据的处理 */
        return doPost(url, data);
    }


    /* 获取用户属性描述信息 */
    public UserMetaList getUserMetaInfo() {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/userConfig"), params);

        ResponseMsg rm = doGet(url);

        if (rm.getCode().equals(StatusCode.FAILURE)) {
            log.error("Failed to get user configuration information, error message: {}", rm.getErrMsg());

            return null;
        }

        return JsonHelper.fromJsonStr(rm.getData(), UserMetaList.class);
    }


    /************************************************************************
    *           物品API
    * */

    /* 更新物品信息，依据method参数确定具体类型 */
    public ResponseMsg updateItem(OperationMethod method, Object t) {
        /* 获取url(包含参数） */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);
        params.put("method", method.getLabel());

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/item"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(t);

        /* 返回数据的处理 */
        return doPost(url, data);
    }

    /* 获取物品信息 */
    public ItemList getItemInfo(ItemIdList items) {
        /* 获取url(包含参数） */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/item"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(items);

        ResponseMsg rm = doPut(url, data);

        if (rm.getCode().equals(StatusCode.FAILURE)) {
            log.error("Failed to get item list, error message: {}", rm.getErrMsg());

            return null;
        }

        return JsonHelper.fromJsonStr(rm.getData(), ItemList.class);
    }


    /* 上传物品属性的描述信息 */
    public ResponseMsg postItemConf(ItemMetaList itemMetaList){
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("biz_code",this.biz_code);
        params.put("token",this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/itemConfig"), params);

        String data = JsonHelper.toJsonStr(itemMetaList);

        return doPost(url,data);
    }

    /* 获取物品属性描述信息 */
    public ItemMetaList getItemConf(){
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("biz_code",this.biz_code);
        params.put("token",this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/itemConfig"), params);

        ResponseMsg rm = doGet(url);

        if (rm.getCode().equals(StatusCode.FAILURE)) {
            log.error("Failed to get item configuration information, error message: {}", rm.getErrMsg());

            return null;
        }

        return JsonHelper.fromJsonStr(rm.getData(),ItemMetaList.class);
    }


    /*
    *           可推荐物品 API
    * */

    /* 更新可推荐物品信息，依据method参数确定具体类型 */
    public ResponseMsg updateEnableRecommenderItems(OperationMethod method, Object t) {
        /* 获取url(包含参数） */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);
        params.put("method", method.getLabel());

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/rec_item"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(t);

        /* 返回数据的处理 */
        return doPost(url, data);
    }

    /* 获取可推荐物品信息 */
    public RecItemList getRecItemInfo(ItemIdList ids) {
        /* 获取url(包含参数） */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biz_code", this.biz_code);
        params.put("token", this.token);

        String url = getRestfulUri(getUrlPrefix("/api/v1/data/rec_item"), params);

        /* 获取Request body数据 */
        String data = JsonHelper.toJsonStr(ids);

        /* 这里不能采用Get，Get方法不能携带有Request Body */
        ResponseMsg rm = doPut(url, data);

        if (rm.getCode().equals(StatusCode.FAILURE)) {
            log.error("Failed to get recommendable item list, error message: {}", rm.getErrMsg());

            return null;
        }

        return JsonHelper.fromJsonStr(rm.getData(), RecItemList.class);
    }

}