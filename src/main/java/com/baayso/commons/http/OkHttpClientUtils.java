package com.baayso.commons.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;

import com.baayso.commons.log.Log;
import com.baayso.commons.web.MediaTypes;

import okhttp3.FormBody;
import okhttp3.MediaType;
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

    private static final OkHttpClient client = new OkHttpClient();

    // 让工具类彻底不可以实例化
    private OkHttpClientUtils() {
        throw new Error("工具类不可以实例化！");
    }

    /**
     * 发送XML参数格式的POST请求。
     *
     * @param url 访问URL
     * @param xml XML格式请求参数
     *
     * @return {@linkplain okhttp3.Response}
     *
     * @since 3.0.0
     */
    public static Response postXml(String url, String xml) {
        // 参数
        RequestBody body = RequestBody.create(MediaType.parse(MediaTypes.APPLICATION_XML_UTF_8), xml);

        return post(url, body);
    }

    /**
     * 发送JSON参数格式的POST请求。
     *
     * @param url  访问URL
     * @param json JSON格式请求参数
     *
     * @return {@linkplain okhttp3.Response}
     *
     * @since 3.0.0
     */
    public static Response postJson(String url, String json) {
        // 参数
        RequestBody body = RequestBody.create(MediaType.parse(MediaTypes.JSON_UTF_8), json);

        return post(url, body);
    }

    /**
     * 发送POST请求。
     *
     * @param url    访问URL
     * @param params 访问参数
     *
     * @return {@linkplain okhttp3.Response}
     *
     * @since 1.0.0
     */
    public static Response post(String url, Map<String, String> params) {
        // 参数
        FormBody.Builder builder = new FormBody.Builder();
        // params.forEach((k, v) -> builder.add(k, v));
        params.forEach(builder::add);
        RequestBody requestBody = builder.build();

        return post(url, requestBody);
    }

    private static Response post(String url, RequestBody body) {
        // 请求
        Request request = new Request.Builder() //
                .url(url) //
                .post(body) //
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return response;
    }

    /**
     * 发送GET请求。
     *
     * @param url 访问URL
     *
     * @return {@linkplain okhttp3.Response}
     *
     * @since 1.0.0
     */
    public static Response get(String url) {
        // 请求
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

        return response;
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

        if (response == null) {
            return "";
        }

        ResponseBody body = response.body();

        if (body == null) {
            return "";
        }

        StringBuilder data = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(body.charStream())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                data.append(line).append('\n');
            }
        }
        catch (Exception e) {
            log.error("读取HTTP响应结果异常！", e);
        }

        return data.toString();
    }

}
