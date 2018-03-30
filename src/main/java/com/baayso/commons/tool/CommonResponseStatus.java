package com.baayso.commons.tool;

/**
 * Common response status。
 *
 * @author ChenFangjie (2015/11/30 13:00)
 * @since 1.0.0
 */
public enum CommonResponseStatus implements ResponseStatus {

    ILLEGAL_DATA(11010032, "非法数据"),

    SERVER_INTERNAL_ERROR(11010064, "服务器内部错误"),

    MISSING_DATA_TOKEN(11010096, "缺少dataToken参数"),

    INVALID_DATA_TOKEN(11010128, "验证数据摘要不通过");


    private final int    value;
    private final String reason;

    private CommonResponseStatus(int value, String reason) {
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
    public static CommonResponseStatus valueOf(int statusCode) {
        for (CommonResponseStatus status : values()) {
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
        return reason;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
