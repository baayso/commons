package com.baayso.commons.security.password;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link com.baayso.commons.security.password.HuToolBCryptPasswordEncoder}
 *
 * @author ChenFangjie (2023/2/28 11:43)
 * @since 1.0.1
 */
public class HuToolBCryptPasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        this.passwordEncoder = new HuToolBCryptPasswordEncoder();
    }

    @AfterEach
    public void tearDown() {
        this.passwordEncoder = null;
    }

    /**
     * Test method for {@link HuToolBCryptPasswordEncoder#encode(CharSequence)}
     *
     * @since 1.0.0
     */
    @Test
    public void testEncode() {
        // $2a$10$Wy3jNC6.O.V605eOGI.Hh.B001ZkeKd6zX5pvYLuHJtFEZ/NMd5CW
        // $2a$10$q6bxmmIkQw0TxIxzMVQnFemoRM.dlY5gc601hxW4mYzFtAR4vGa9O
        String rawPassword = "123456";
        String encodedPassword = this.passwordEncoder.encode(rawPassword);
        Assertions.assertTrue(this.passwordEncoder.matches(rawPassword, encodedPassword));
        System.out.println(encodedPassword);

        // $2a$10$5C8hJcKZ4Pcjdo6egPETl.FuIbpZkEhJhEBAUUUWfQB5NX9oLIOse
        // $2a$10$Q.gKi35CuRcSycsGcqBfQeUi6KbeFUXIhgtcIbKMBHlwEYoqKaDZi
        rawPassword = "abc654321";
        encodedPassword = this.passwordEncoder.encode(rawPassword);
        Assertions.assertTrue(this.passwordEncoder.matches(rawPassword, encodedPassword));
        System.out.println(encodedPassword);
    }

    /**
     * Test method for {@link HuToolBCryptPasswordEncoder#matches(CharSequence, String)}
     *
     * @since 1.0.0
     */
    @Test
    public void testMatches() {
        String encodedPassword = "$2a$10$q6bxmmIkQw0TxIxzMVQnFemoRM.dlY5gc601hxW4mYzFtAR4vGa9O";
        Assertions.assertTrue(this.passwordEncoder.matches("123456", encodedPassword));

        encodedPassword = "$2a$10$5C8hJcKZ4Pcjdo6egPETl.FuIbpZkEhJhEBAUUUWfQB5NX9oLIOse";
        Assertions.assertTrue(this.passwordEncoder.matches("abc654321", encodedPassword));

        encodedPassword = "$2a$10$5C8hJcKZ4Pcjdo6egPETl.FuIbpZkEhJhEBABCDWfQB5NX9oLIOse";
        Assertions.assertFalse(this.passwordEncoder.matches("abc654321", encodedPassword));
    }

    /**
     * Test method for {@link HuToolBCryptPasswordEncoder#upgradeEncoding(String)}
     *
     * @since 1.0.0
     */
    @Test
    public void testUpgradeEncoding() {
        String encodedPassword = "$2a$10$q6bxmmIkQw0TxIxzMVQnFemoRM.dlY5gc601hxW4mYzFtAR4vGa9O";
        Assertions.assertFalse(this.passwordEncoder.upgradeEncoding(encodedPassword));
    }

}
