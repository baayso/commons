package com.baayso.commons.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link com.baayso.commons.http.OkHttpClientUtils}.
 *
 * @author ChenFangjie (2016/3/31 9:05)
 * @since 1.0.0
 */
public class OkHttpClientUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.baayso.commons.http.OkHttpClientUtils#post(java.lang.String, java.util.Map)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testPost() {

        Map<String, String> params = new HashMap<>();
        params.put("CHN", "CHN");

        String body = OkHttpClientUtils.post("http://www.baidu.com", params);

        System.out.println(body);
    }

    /**
     * Test method for {@link com.baayso.commons.http.OkHttpClientUtils#get(java.lang.String)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testGet() {
        String body = OkHttpClientUtils.get("https://github.com/baayso");

        System.out.println(body);
    }

}
