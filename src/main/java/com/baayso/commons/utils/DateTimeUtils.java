package com.baayso.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * DateTime工具类。
 *
 * @author ChenFangjie (2020/7/11 15:35)
 * @since 1.0.1
 */
public final class DateTimeUtils {

    public static final DateTimeFormatter DATE_FORMATTER_SEPARATOR      = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER_SEPARATOR      = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_SEPARATOR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER_NO_SEPARATOR      = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter TIME_FORMATTER_NO_SEPARATOR      = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_NO_SEPARATOR = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter LONG_DATE_TIME_FORMATTER_SEPARATOR    = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter LONG_DATE_TIME_FORMATTER_NO_SEPARATOR = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static final ThreadLocal<DateFormat> SIMPLE_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /** 星期 */
    private static final String[] WEEKDAY = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private DateTimeUtils() {
    }

    public static String nowDateSeparator() {
        return LocalDate.now().format(DateTimeUtils.DATE_FORMATTER_SEPARATOR);
    }

    public static String nowDateNoSeparator() {
        return LocalDate.now().format(DateTimeUtils.DATE_FORMATTER_NO_SEPARATOR);
    }

    public static String nowTimeSeparator() {
        return LocalTime.now().format(DateTimeUtils.TIME_FORMATTER_SEPARATOR);
    }

    public static String nowTimeNoSeparator() {
        return LocalTime.now().format(DateTimeUtils.TIME_FORMATTER_NO_SEPARATOR);
    }

    public static String nowDateTimeSeparator() {
        return LocalDateTime.now().format(DateTimeUtils.DATE_TIME_FORMATTER_SEPARATOR);
    }

    public static String nowDateTimeNoSeparator() {
        return LocalDateTime.now().format(DateTimeUtils.DATE_TIME_FORMATTER_NO_SEPARATOR);
    }

    /** Date 转 LocalDate */
    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    /** Date 转 LocalDateTime */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }

        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zone);

        return zonedDateTime.toLocalDateTime();
    }

    /** LocalDate 转 Date */
    public static Date localDateToDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }

        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());

        return Date.from(zonedDateTime.toInstant());
    }

    /** LocalDateTime 转 Date */
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /** 格式化日期，格式为：yyyy-MM-dd */
    public static String formatDate(Date date) {
        LocalDate localDate = dateToLocalDate(date);

        return localDate.format(DATE_FORMATTER_SEPARATOR);
    }

    /** 格式化日期时间，格式为：yyyy-MM-dd HH:mm:ss */
    public static String formatDateTime(Date date) {
        LocalDateTime dateTime = dateToLocalDateTime(date);

        return dateTime.format(DATE_TIME_FORMATTER_SEPARATOR);
    }

    /**
     * 根据时间获取是上午还是下午（整数表示）。
     *
     * @return 0:上午、1:下午
     */
    public static int getAmPm(LocalTime localTime) {
        // 0:上午  1:下午
        return localTime.get(ChronoField.HOUR_OF_AMPM);
    }

    /**
     * 根据时间获取是上午还是下午（汉字表示）。
     *
     * @return 上午、下午
     */
    public static String getStrAmPm(LocalTime localTime) {
        // 0:上午  1:下午
        int ampm = localTime.get(ChronoField.HOUR_OF_AMPM);
        return ampm == 0 ? "上午" : "下午";
    }

    /**
     * 根据日期时间获取是上午还是下午（整数表示）。
     *
     * @return 0:上午、1:下午
     */
    public static int getAmPm(LocalDateTime localDateTime) {
        // 0:上午  1:下午
        return localDateTime.get(ChronoField.AMPM_OF_DAY);
    }

    /**
     * 根据日期时间获取是上午还是下午（汉字表示）。
     *
     * @return 上午、下午
     */
    public static String getStrAmPm(LocalDateTime localDateTime) {
        // 0:上午  1:下午
        int ampm = localDateTime.get(ChronoField.AMPM_OF_DAY);
        return ampm == 0 ? "上午" : "下午";
    }

    /** 根据数字返回汉字“周几” */
    public static String getWeek(int weekValue) {
        if (weekValue < 1 || weekValue > 7) {
            return "";
        }

        return DateTimeUtils.WEEKDAY[weekValue - 1];
    }

    /** 根据DayOfWeek枚举返回汉字“周几” */
    public static String getWeek(DayOfWeek dayOfWeek) {
        return DateTimeUtils.WEEKDAY[dayOfWeek.ordinal()];
    }

    public static LocalDate getMonday(LocalDate localDate) {
        return localDate.with(DayOfWeek.MONDAY);
    }

    public static LocalDateTime getMonday(LocalDateTime localDateTime) {
        return localDateTime.with(DayOfWeek.MONDAY);
    }

    public LocalDate getSunday(LocalDate localDate) {
        return localDate.with(DayOfWeek.SUNDAY);
    }

    public LocalDateTime getSunday(LocalDateTime localDateTime) {
        return localDateTime.with(DayOfWeek.SUNDAY);
    }

    /** 根据出生日期计算年龄 */
    public static int getAge(LocalDate birthday) {
        if (birthday == null) {
            return 0;
        }

        return birthday.until(LocalDate.now()).getYears();
    }

}
