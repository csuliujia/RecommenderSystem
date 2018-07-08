package com.dtdream.DtRecommender.sdk.base;

import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.apache.commons.httpclient.HttpStatus.getStatusText;

/**
 * Created by handou on 8/20/16.
 */
public class HttpMethod {
    private final static Logger log = LoggerFactory.getLogger(HttpMethod.class);
    /**
     * Post操作
     *
     * @param url  请求的url
     * @param data Request body数据
     * @return 处理的code值
     */
    public static ResponseMsg doPost(String url, String data) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        int responseCode;
        String body;

        ResponseMsg result = null;

        /* 构造请求数据 */
        StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);

        /* 设置请求体Request body */
        post.setEntity(myEntity);

        /* 响应内容 */
        CloseableHttpResponse response = null;

        try {
            response = client.execute(post);

            // 获取响应实体
            HttpEntity entity = response.getEntity();

            // 打印响应状态
            responseCode = response.getStatusLine().getStatusCode();
            log.debug(String.format("Method: Post, result: code=%d %s.\r\n", responseCode, getStatusText(responseCode)));

            if (entity != null) {

                // 打印响应内容
                body = EntityUtils.toString(entity);
                log.debug("Response content: " + body);

                result = JsonHelper.fromJsonStr(body, ResponseMsg.class);
            } else {
                result = new ResponseMsg();
                result.setCode(StatusCode.FAILURE);
                result.setErrMsg("Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (client != null)
                        client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    /**
     * Get操作
     *
     * @param url
     * @return
     */
    public static ResponseMsg doGet(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        int responseCode;
        String body;

        ResponseMsg result = null;

        try {
            httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            response = httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity = response.getEntity();

            // 打印响应状态
            responseCode = response.getStatusLine().getStatusCode();
            log.debug("Method: Get, result: code={} {}.", responseCode, getStatusText(responseCode));

            if (entity != null) {

                // 打印响应内容
                body = EntityUtils.toString(entity);
                log.debug("Response content: " + body);

                result = JsonHelper.fromJsonStr(body, ResponseMsg.class);
            } else {
                result = new ResponseMsg();
                result.setCode(StatusCode.FAILURE);
                result.setErrMsg("Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpclient != null) {
                        httpclient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    public static ResponseMsg doPut(String url, String data) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        int responseCode;
        String body;

        ResponseMsg result = null;

        try {
            httpclient = HttpClients.createDefault();

            // 创建httpget.
            HttpPut put = new HttpPut(url);

            /* 构造请求数据 */
            StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);

            /* 设置请求体Request body */
            put.setEntity(myEntity);

            // 执行put请求.
            response = httpclient.execute(put);

            // 获取响应实体
            HttpEntity entity = response.getEntity();

            // 打印响应状态
            responseCode = response.getStatusLine().getStatusCode();
            log.debug("Method: Put, result: code={} {}.", responseCode, getStatusText(responseCode));

            if (entity != null) {

                // 打印响应内容
                body = EntityUtils.toString(entity);
                log.debug("Response content: " + body);

                result = JsonHelper.fromJsonStr(body, ResponseMsg.class);
            } else {
                result = new ResponseMsg();
                result.setCode(StatusCode.FAILURE);
                result.setErrMsg("Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpclient != null) {
                        httpclient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }



    /* Delete操作 */

    /**
     *
     * @param url
     * @return  返回删除的状态码
     */
    public static int doDelete(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse deleteResponse = null;
        int responseCode = HttpStatus.SC_OK;

        try {
            httpclient = HttpClients.createDefault();

            HttpDelete httpDelete = new HttpDelete(url);

            deleteResponse = httpclient.execute(httpDelete);

            HttpEntity entity = deleteResponse.getEntity();

            responseCode = deleteResponse.getStatusLine().getStatusCode();
            log.debug(String.format("Method: Delete, result: code=%d %s.\r\n", responseCode, getStatusText(responseCode)));

            if (entity != null) {
                // 打印响应内容
                log.debug("Response content: " + EntityUtils.toString(entity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (deleteResponse != null)
                    deleteResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpclient != null)
                        httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseCode;
    }

}
