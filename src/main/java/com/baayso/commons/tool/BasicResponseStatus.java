package com.baayso.commons.tool;

/**
 * 基础响应状态。
 *
 * @author ChenFangjie (2020/8/8 14:09)
 * @since 1.0.1
 */
public enum BasicResponseStatus implements ResponseStatus {

    OK(200, "执行成功"),

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未进行身份验证"),
    FORBIDDEN(403, "拒绝请求"),
    NOT_FOUND(404, "请求路径(URL)有误"),
    METHOD_NOT_ALLOWED(405, "请求方法不能被用于请求相应的资源"),

    PARAMETER_MISSING(4000, "缺少请求参数"),
    PARAMETER_TYPE_ERROR(4001, "请求参数类型不匹配"),
    PARAMETER_CHECK_FAILED(4002, "请求参数校验失败"),
    CONTENT_TYPE_NOT_SUPPORTED(4003, "Content-Type属性设置错误"),
    REQUEST_BODY_DATA_CONVERT_ERROR(4004, "请求体(RequestBody)数据转换出错"),
    DATA_TOKEN_MISSING(4091, "缺少dataToken参数"),
    DATA_TOKEN_CHECK_FAILED(4092, "数据摘要验证不通过"),

    SERVER_INTERNAL_ERROR(500, "服务器遇到错误，无法完成请求"),
    NOT_IMPLEMENTED(501, "暂未实现此功能"),
    SERVICE_UNAVAILABLE(503, "服务器当前无法处理请求，请稍后重试"),

    ERROR(5000, "执行失败"),
    ;

    private final int    value;
    private final String reason;

    private BasicResponseStatus(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    /**
     * Return the enum constant of this type with the specified numeric value.
     *
     * @param statusCode the numeric value of the enum to be returned
     *
     * @return the enum constant with the specified numeric value
     *
     * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
     * @since 1.0.0
     */
    public static BasicResponseStatus valueOf(int statusCode) {
        for (BasicResponseStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

    /**
     * Return the integer value of this status code.
     */
    @Override
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    @Override
    public String getReason() {
        return this.reason;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

}
