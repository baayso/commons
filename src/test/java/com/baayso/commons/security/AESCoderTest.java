package com.baayso.commons.security;

import java.util.Base64;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link com.baayso.commons.security.AESCoder}.
 *
 * @author ChenFangjie (2015年1月10日 下午6:04:07)
 * @since 1.0.0
 */
public class AESCoderTest {

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.baayso.commons.security.AESCoder#encryptStr(String)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testEncryptStr() {
        AESCoder aesCoder = new AESCoder("123456");
        String actual = aesCoder.encryptStr("postgres");

        System.out.println(actual);

        assertEquals("v/VxFAgjZDRwoZ0FnAbKgw==", actual);
    }

    /**
     * Test method for {@link com.baayso.commons.security.AESCoder#decryptStr(String)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testDecryptStr() {
        AESCoder aesCoder = new AESCoder("123456");
        String actual = aesCoder.decryptStr("v/VxFAgjZDRwoZ0FnAbKgw==");
        assertEquals("postgres", actual);
    }

    /**
     * Test method for {@link com.baayso.commons.security.AESCoder#initKey()}.
     *
     * @since 1.0.0
     */
    @Test
    public void testInitKey() {
        byte[] actual = AESCoder.initKey();
        System.out.println(Base64.getEncoder().encodeToString(actual));
    }

    /**
     * Test method for {@link com.baayso.commons.security.AESCoder#toKey(byte[])}.
     *
     * @since 1.0.0
     */
    @Test
    public void testToKey() {
        AESCoder aesCoder = new AESCoder("123456");
        byte[] expected = AESCoder.initKey();
        byte[] actual = aesCoder.toKey(expected).getEncoded();
        assertEquals(Base64.getEncoder().encodeToString(expected), org.apache.commons.codec.binary.Base64.encodeBase64String(actual));
    }

    /**
     * Test method for {@link com.baayso.commons.security.AESCoder#encrypt(byte[], byte[])}.
     *
     * @since 1.0.0
     */
    @Test
    public void testEncrypt() {
    }

    /**
     * Test method for {@link com.baayso.commons.security.AESCoder#decrypt(byte[], byte[])}.
     *
     * @since 1.0.0
     */
    @Test
    public void testDecrypt() {
    }

}
