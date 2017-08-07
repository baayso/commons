package com.baayso.commons.mybatis.type;

/**
 * 自定义Mybatis存储 Enum Value 接口。
 *
 * @author ChenFangjie (2017/8/7 13:59)
 * @since 1.0.0
 */
public interface ValueEnum {

    /**
     * Return the integer value of this status code.
     *
     * @since 1.0.0
     */
    int getValue();

    /**
     * Return the reason phrase of this status code.
     *
     * @since 1.0.0
     */
    String getDesc();

}
