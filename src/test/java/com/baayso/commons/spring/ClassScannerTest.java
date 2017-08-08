package com.baayso.commons.spring;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;

import com.baayso.commons.tool.ResponseStatus;

/**
 * Test class for {@link ClassScanner}.
 *
 * @author ChenFangjie (2017/8/8 10:17)
 * @since 1.0.0
 */
public class ClassScannerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link ClassScanner#getClassSet()}.
     *
     * @since 1.0.0
     */
    @Test
    public void testGetClassSet() throws IOException, ClassNotFoundException {
        ClassScanner scanner = new ClassScanner(new String[]{"com.baayso"}, Service.class);

        System.out.println("\n" + scanner.getClassSet());
    }

    /**
     * Test method for {@link ClassScanner#getClassSet()}.
     *
     * @since 1.0.0
     */
    @Test
    public void testGetClassSet2() throws IOException, ClassNotFoundException {
        ClassScanner scanner = new ClassScanner(new String[]{"com.baayso"}, ResponseStatus.class);

        System.out.println("\n" + scanner.getClassSet());
    }

}
