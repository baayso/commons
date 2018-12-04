package com.baayso.commons.security;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vip.vjtools.vjkit.text.EncodeUtil;

import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link PasswordUtils}.
 *
 * @author ChenFangjie (2016年5月25日 下午4:44:44)
 * @since 1.0.0
 */
public class PasswordUtilsTest {

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
     * Test method for {@link PasswordUtils#generateSalt()}.
     *
     * @since 1.0.0
     */
    @Test
    public void testGenerateSalt() {
        System.out.println(Arrays.toString(PasswordUtils.generateSalt()));
        System.out.println(EncodeUtil.encodeHex(PasswordUtils.generateSalt()));
    }

    /**
     * Test method for {@link PasswordUtils#encryptPassword(String, byte[])}.
     *
     * @since 1.0.0
     */
    @Test
    public void testEncryptPassword() {
        byte[] bSalt = PasswordUtils.generateSalt();
        String salt = EncodeUtil.encodeHex(bSalt);

        System.out.println(salt);

        System.out.println(PasswordUtils.encryptPassword("admin", bSalt));
    }

    /**
     * Test method for {@link PasswordUtils#matches(String, String, String)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testMatches() {
        boolean result = PasswordUtils.matches("98a07917839825d026bc18b8333e3388f2ed03c2", "d3698101a2e16c3f", "admin");

        assertTrue(result);
    }

}
