package com.baayso.commons.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.baayso.commons.log.Log;

/**
 * HTTP请求工具类。
 *
 * @author ChenFangjie (2015年7月11日 下午8:12:09)
 * @since 1.0.0
 */
public final class HttpClientUtils {

    private static final Logger log = Log.get();

    // 让工具类彻底不可以实例化
    private HttpClientUtils() {
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
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = HttpClientUtils.createSSLClientDefault();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        HttpPost post = new HttpPost(url);

        post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

        String data = "";

        try (CloseableHttpResponse response = httpClient.execute(post);) {

            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            data = HttpClientUtils.readResponse(in);

            // 消耗掉response
            EntityUtils.consume(entity);
        }
        catch (Exception e) {
            log.error("发送HTTP POST请求异常！", e);
        }

        return data;
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
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = HttpClientUtils.createSSLClientDefault();

        HttpGet get = new HttpGet(url);

        String data = "";

        try (CloseableHttpResponse response = httpClient.execute(get)) {

            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            data = HttpClientUtils.readResponse(in);

            // 消耗掉response
            EntityUtils.consume(entity);
        }
        catch (Exception e) {
            log.error("发送HTTP GET请求异常！", e);
        }

        return data;
    }

    /**
     * 读取响应。
     *
     * @param in {@linkplain java.io.InputStream}
     *
     * @return 响应内容
     *
     * @since 1.0.0
     */
    public static String readResponse(InputStream in) {
        StringBuilder data = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
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

    /**
     * 创建 SSL HTTP Client。
     *
     * @return SSL HTTP Client
     *
     * @since 1.0.0
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        catch (KeyManagementException e) {
            log.error(e.getMessage(), e);
        }
        catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
        }

        return HttpClients.createDefault();
    }

}
