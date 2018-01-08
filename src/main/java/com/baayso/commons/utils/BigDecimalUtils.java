package com.baayso.commons.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 工具类。
 *
 * @author ChenFangjie (2018/1/8 16:23)
 * @since 1.0.0
 */
public class BigDecimalUtils {

    /** 默认小数规模：保留2位小数 */
    public final static int          DEFAULT_SCALE         = 2;
    /** 默认舍入方式：四舍五入 */
    public final static RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;


    /**
     * 使用四舍五入并保留2位小数的除法计算。<br>
     * 除数为0时会返回0。
     *
     * @param dividend 被除数
     * @param divisor  除数
     *
     * @return 商
     *
     * @since 1.0.0
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return BigDecimalUtils.divide(dividend, divisor, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 保留指定小数位数的除法计算。<br>
     * 舍入方式使用四舍五入。<br>
     * 除数为0时会返回0。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    小数规模（保留几位小数）
     *
     * @return 商
     *
     * @since 1.0.0
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
        return BigDecimalUtils.divide(dividend, divisor, scale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 使用指定舍入方式的除法计算。<br>
     * 默认保留2位小数。<br>
     * 除数为0时会返回0。
     *
     * @param dividend     被除数
     * @param divisor      除数
     * @param roundingMode 舍入方式
     *
     * @return 商
     *
     * @since 1.0.0
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, RoundingMode roundingMode) {
        return BigDecimalUtils.divide(dividend, divisor, DEFAULT_SCALE, roundingMode);
    }

    /**
     * 使用指定舍入方式和指定小数位数的除法计算。<br>
     * 除数为0时会返回0。
     *
     * @param dividend     被除数
     * @param divisor      除数
     * @param scale        小数规模（保留几位小数）
     * @param roundingMode 舍入方式
     *
     * @return 商
     *
     * @since 1.0.0
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMode) {
        return BigDecimal.ZERO.equals(dividend) || BigDecimal.ZERO.equals(divisor) //
                ? BigDecimal.ZERO //
                : dividend.divide(divisor, scale, roundingMode);
    }

}
