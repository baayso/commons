package com.baayso.commons.http;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;

import com.baayso.commons.log.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HTTP请求工具类。
 *
 * @author ChenFangjie (2016/3/30 15:21)
 * @since 1.0.0
 */
public final class OkHttpClientUtils {

    private static final Logger log = Log.get();

    // 让工具类彻底不可以实例化
    private OkHttpClientUtils() {
        throw new Error("工具类不可以实例化！");
    }

    /**
     * 发送POST请求。
     *
     * @param url    访问url
     * @param params 访问参数
     *
     * @return 响应内容
     *
     * @since 1.0.0
     */
    public static String post(String url, Map<String, String> params) {
        OkHttpClient client = new OkHttpClient();

        // 参数
        FormBody.Builder builder = new FormBody.Builder();
        // params.forEach((k, v) -> formBodyBuilder.add(k, v));
        params.forEach(builder::add);
        RequestBody requestBody = builder.build();

        // 请求
        Request request = new Request.Builder() //
                .url(url) //
                .post(requestBody) //
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return readResponse(response);
    }

    /**
     * 发送GET请求。
     *
     * @param url 访问url
     *
     * @return 响应内容
     *
     * @since 1.0.0
     */
    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder() //
                .url(url) //
                .get() //
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return readResponse(response);
    }

    /**
     * 读取响应。
     *
     * @param response {@linkplain okhttp3.Response}
     *
     * @return 响应内容
     *
     * @since 1.0.0
     */
    public static String readResponse(Response response) {
        String bodyStr = "";

        if (response == null) {
            return bodyStr;
        }

        ResponseBody body = response.body();

        try {
            bodyStr = body.string();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return bodyStr;
    }

}
