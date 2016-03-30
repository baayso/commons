package com.baayso.commons.tool;

/**
 * 响应状态接口。
 *
 * @author ChenFangjie (2015/12/9 19:23)
 * @since 1.0.0
 */
public interface ResponseStatus {

    /**
     * Return the integer value of this status code.
     *
     * @since 1.0.0
     */
    int value();

    /**
     * Return the reason phrase of this status code.
     *
     * @since 1.0.0
     */
    String getReason();

}
