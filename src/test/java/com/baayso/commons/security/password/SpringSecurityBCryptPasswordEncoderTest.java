package com.baayso.commons.security.password;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link com.baayso.commons.security.password.SpringSecurityBCryptPasswordEncoderTest}
 *
 * @author ChenFangjie (2023/2/28 12:27)
 * @since 1.0.1
 */
public class SpringSecurityBCryptPasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        this.passwordEncoder = new SpringSecurityBCryptPasswordEncoder();
    }

    @After
    public void tearDown() {
        this.passwordEncoder = null;
    }


    /**
     * Test method for {@link SpringSecurityBCryptPasswordEncoder#encode(CharSequence)}
     *
     * @since 1.0.0
     */
    @Test
    public void testEncode() {
        // $2a$10$Wy3jNC6.O.V605eOGI.Hh.B001ZkeKd6zX5pvYLuHJtFEZ/NMd5CW
        // $2a$10$q6bxmmIkQw0TxIxzMVQnFemoRM.dlY5gc601hxW4mYzFtAR4vGa9O
        String rawPassword = "123456";
        String encodedPassword = this.passwordEncoder.encode(rawPassword);
        Assert.assertTrue(this.passwordEncoder.matches(rawPassword, encodedPassword));
        System.out.println(encodedPassword);

        // $2a$10$5C8hJcKZ4Pcjdo6egPETl.FuIbpZkEhJhEBAUUUWfQB5NX9oLIOse
        // $2a$10$Q.gKi35CuRcSycsGcqBfQeUi6KbeFUXIhgtcIbKMBHlwEYoqKaDZi
        rawPassword = "abc654321";
        encodedPassword = this.passwordEncoder.encode(rawPassword);
        Assert.assertTrue(this.passwordEncoder.matches(rawPassword, encodedPassword));
        System.out.println(encodedPassword);
    }

    /**
     * Test method for {@link SpringSecurityBCryptPasswordEncoder#matches(CharSequence, String)}
     *
     * @since 1.0.0
     */
    @Test
    public void testMatches() {
        String encodedPassword = "$2a$10$q6bxmmIkQw0TxIxzMVQnFemoRM.dlY5gc601hxW4mYzFtAR4vGa9O";
        Assert.assertTrue(this.passwordEncoder.matches("123456", encodedPassword));

        encodedPassword = "$2a$10$5C8hJcKZ4Pcjdo6egPETl.FuIbpZkEhJhEBAUUUWfQB5NX9oLIOse";
        Assert.assertTrue(this.passwordEncoder.matches("abc654321", encodedPassword));

        encodedPassword = "$2a$10$5C8hJcKZ4Pcjdo6egPETl.FuIbpZkEhJhEBABCDWfQB5NX9oLIOse";
        Assert.assertFalse(this.passwordEncoder.matches("abc654321", encodedPassword));
    }

    /**
     * Test method for {@link SpringSecurityBCryptPasswordEncoder#upgradeEncoding(String)}
     *
     * @since 1.0.0
     */
    @Test
    public void testUpgradeEncoding() {
        String encodedPassword = "$2a$10$q6bxmmIkQw0TxIxzMVQnFemoRM.dlY5gc601hxW4mYzFtAR4vGa9O";
        Assert.assertFalse(this.passwordEncoder.upgradeEncoding(encodedPassword));
    }

}
