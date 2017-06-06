package com.baayso.commons.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link com.baayso.commons.utils.Validator}.
 *
 * @author ChenFangjie (2017/6/6 15:14)
 * @since 1.0.0
 */
public class ValidatorTest {

    /**
     * Test method for {@link com.baayso.commons.utils.Validator#isBoolean(String)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testIsBoolean() {
        Validator validator = new Validator();

        System.out.println(validator.isBoolean("true"));  // true
        System.out.println(validator.isBoolean("false")); // true

        System.out.println(validator.isBoolean("1")); // false
        System.out.println(validator.isBoolean("0")); // false

        assertEquals(validator.isBoolean("123"), false);
    }

}
