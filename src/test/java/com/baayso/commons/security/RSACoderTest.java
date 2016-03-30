package com.baayso.commons.security;

import java.security.Key;
import java.util.Base64;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link com.baayso.commons.security.RSACoder}.
 *
 * @author ChenFangjie (2015/1/11 15:33:57)
 * @since 1.0.0
 */
public class RSACoderTest {

    /* 公钥 */
    private byte[] publicKey;

    /* 私钥 */
    private byte[] privateKey;

    /**
     * 初始化密钥。
     *
     * @throws Exception
     * @since 1.0.0
     */
    @Before
    public void setUp() throws Exception {
        Map<String, Key> keyMap = RSACoder.initKey(2048);

        this.publicKey = RSACoder.getPublicKey(keyMap);
        this.privateKey = RSACoder.getPrivateKey(keyMap);

        System.err.println("公钥: \n" + Base64.getEncoder().encodeToString(this.publicKey));
        System.err.println("私钥： \n" + Base64.getEncoder().encodeToString(this.privateKey));
    }

    /**
     * Test method for {@link com.baayso.commons.security.RSACoder#sign(byte[], byte[])}.
     *
     * @since 1.0.0
     */
    @Test
    public void testSign() {
        String inputStr = "The People's Republic of China";
        byte[] data = StringUtils.getBytesUtf8(inputStr);

        // 产生签名
        byte[] sign = RSACoder.sign(data, this.privateKey);
        System.err.println("签名:\n" + Base64.getEncoder().encodeToString(sign));

        // 验证签名
        boolean status = RSACoder.verify(data, this.publicKey, sign);
        System.err.println("状态:\n" + status);

        assertTrue(status);
    }

    /**
     * Test method for {@link com.baayso.commons.security.RSACoder#sign(byte[], byte[])}.
     *
     * @since 1.0.0
     */
    @Test
    public void testSign_2() {
        String inputStr = "The People's Republic of China";
        byte[] data = StringUtils.getBytesUtf8(inputStr);

        final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCf40BYWqB/upR9f38KAxhp24D2ASlgItCVpaV3jp23/pIpx4N2/2f7WmXNvlLHPRxt8uyNq7q2mYx0EimhKxc0TKb1g8/9OCzO1/nLVT/uV+74ZfeLjdOpGv7o14KTfFFMsBVQSRU6vz5QJYj75XYgsmUn9wGaLoUglcxObls9pQIDAQAB";
        final String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJSZa5i7UG+wqIWgg8LJuGB1V4Nheh+7O/JSyOvXuhmlIj6exuqGLWcKFW9DMHdS/TgMxslWeu55znT6qnt++a1yfLuIIqAqIwl/jjcjAFpYp6ZqggGmTqjZ/iwshsVxewqLhLLKQOUaXmmMOBP0iTp/uzy0nTC8nRJLUeDhM0kFAgMBAAECgYBgQ07vfxCpegCBqC0TB4g4fGt3wbLSUYGRBk4AchyttksL0+G6Vbed02t1S0pfUlSVdblk/m/eguva3fOI0wW8ct1fouO+pzFJQLkN4GmBPeLZN5aOyM1E8KEV4wQzBWFvb8h7UlejyVAWbBulHvzxU0ECq/1NGMKDdZiCtz4egQJBAPMcfCXNuHAnpb9KXtKxOUvC7I2fkHzi5e20fes/FHqypKcb1CcemBJGBNjWhLnM8IsjF39clsHgbENbB6pQbSUCQQCcejclSd3+nWYRBAnxcCtJa9NcVxamjzZ8plt7VFqWh9MkEWShB5+wodQpmN7ZwxUspwP7sm16cmIAPwM7edZhAkEAghQJsUiDmS79ln66gc1ltM3VOGnaK5hUIn3iqg6UbJpNU0SmnU/XWl+wlD0jwy6OvkPGfNUTrSThiKCaV1y1KQJBAJhYyV4NqAygtGH2u7OM0Sg/yd4KkGwD3TnJQiI2Q6hxf1mLcZzybkCrnoQNaVM9A12hEli5JKvlt5KjxwLeIaECQQDOrkk2CfBmNzKTmFWWa/4YPT79Ng1QLEAzDVqoZcDQTbRQRjCteDnpvQs6ty3kBXX0kCL42QmHlyeLF17lHU+H";

        // 产生签名
        byte[] sign = RSACoder.sign(data, Base64.getDecoder().decode(privateKey));
        // System.err.println("签名:\n" + Base64.encodeBase64String(sign));

        // 验证签名
        boolean status = RSACoder.verify(data, Base64.getDecoder().decode(publicKey), sign);
        // System.err.println("状态:\n" + status);

        assertTrue(status);
    }

}
