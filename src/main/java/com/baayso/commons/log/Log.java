package com.baayso.commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对 slf4j Logger 的简单封装。
 *
 * @author ChenFangjie (2015/9/20 11:26:27)
 * @since 1.0.0
 */
public class Log {

    public static Logger get(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger get(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static Logger get() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return LoggerFactory.getLogger(stackTrace[2].getClassName());
    }

}
