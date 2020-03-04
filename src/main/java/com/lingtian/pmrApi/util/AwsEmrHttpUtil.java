package com.lingtian.pmrApi.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zushilong
 * @version 1.0
 * @description get post工具类
 * @Create 2017-06-16
 */
public class AwsEmrHttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AwsEmrHttpUtil.class);
    private static final OkHttpClient CLIENT;

    //存放用户的token
    public static Map<String,String> LOGIN_COOKIE = new HashMap();

    static {
        CLIENT = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) //连接超时
                .writeTimeout(30, TimeUnit.SECONDS) //写超时
                .readTimeout(30, TimeUnit.SECONDS) //读超时
                .build();
    }

    public enum OkHttpMethod {
        POST,
        PUT,
        DELETE
    }


    /**
     * get请求,支持http和https
     *
     * @param url     地址,比如: http://wwww.baidu.com
     * @param params  参数,可以为null
     * @param headers 请求头,可以为null
     * @return
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers) {
        //Builder对象
        Request.Builder builder = new Request.Builder();

        //处理参数
        if (null != params && params.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("?");
            params.forEach((k, v) -> {
                stringBuilder.append(k).append("=").append(v).append("&");
            });
            String param = stringBuilder.toString();
            url += param.substring(0, param.lastIndexOf("&"));
        }

        //处理请求头
        if (null != headers && headers.size() > 0) {
            headers.forEach((k, v) -> builder.header(k, v));
        }

        Request request = builder.url(url).build();

        //创建响应对象
        try {
            Response response = CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                LOGGER.error("发送get请求失败,状态码:{}", response.code());
                return "";
            }
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error("发送get请求失败,原因:{}", e.getCause());
            return "";
        }
    }

    /**
     * form表单
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String postForm(String url, Map<String, String> params, Map<String, String> headers) {
        //Builder对象
        LOGGER.info("postOrPutOrDelete-begin url=" + url);
        //处理请求头
        FormBody.Builder build = new FormBody.Builder();

        if (null != params && params.size() > 0) {
            params.forEach((k, v) -> build.add(k, v));
        }
        FormBody formBody = build.build();
        final Request request = new Request.Builder()
                .url(url)//请求的url
                .post(formBody)
                .build();
        try {
            Response response = CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                LOGGER.error("发送请求失败,状态码:{}", response.code());
                return "";
            }
            System.out.println(response.headers().toString());

            List<String> cookieList = response.headers().values("Set-Cookie");
            System.out.println(JSON.toJSONString(cookieList));
            StringBuilder builderStr = new StringBuilder();
            if (cookieList != null && cookieList.size() > 1) {
                for (String cookieValue : cookieList) {
                    builderStr.append(cookieValue);
                    builderStr.append("; ");
                }
                LOGIN_COOKIE.put(params.get("uID"), builderStr.toString());
            }
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error("发送请求失败,原因:{}", e.getCause());
            return "";
        } finally {
            LOGGER.info("postOrPutOrDelete-end");
        }
    }

    /**
     * post,put,delete请求,支持http和https
     *
     * @param url          地址,比如: http://wwww.baidu.com
     * @param jsonParams   请求参数 json格式字符串
     * @param headers      请求头,可以为null
     * @param okHttpMethod 请求方式
     * @return
     */
    public static String postOrPutOrDelete(String url, String jsonParams, Map<String, String> headers, OkHttpMethod okHttpMethod) {
        //Builder对象
        Request.Builder builder = new Request.Builder();
        LOGGER.info("postOrPutOrDelete-begin url=" + url);
        //处理请求头
        if (null != headers && headers.size() > 0) {
            headers.forEach((k, v) -> builder.header(k, v));
        }
        //处理参数
        if (!StringUtils.isEmpty(jsonParams)) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
            switch (okHttpMethod) {
                case POST:
                    builder.post(body);
                    break;
                case PUT:
                    builder.put(body);
                    break;
                case DELETE:
                    builder.delete(body);
                    break;
                default:
                    builder.post(body);
                    break;

            }
        } else {
            switch (okHttpMethod) {
                case DELETE:
                default:
                    builder.delete();
                    break;
            }
        }
        Request request = builder.url(url).build();
        //创建响应对象
        try {
            Response response = CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                LOGGER.error("发送请求失败,状态码:{}", response.code());
                return "";
            }
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error("发送请求失败,原因:{}", e.getCause());
            return "";
        } finally {
            LOGGER.info("postOrPutOrDelete-end");
        }
    }

}