package com.baayso.commons.utils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * DateTime工具类。
 *
 * @author ChenFangjie (2020/7/11 15:35)
 * @since 1.0.1
 */
public final class DateTimeUtils {

    public static final DateTimeFormatter DATE_FORMATTER_SEPARATOR      = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_SEPARATOR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER_NO_SEPARATOR      = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_NO_SEPARATOR = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /** 星期 */
    private static final Map<String, String> WEEKDAY = new HashMap<String, String>(7) {
        {
            put("1", "周一");
            put("2", "周二");
            put("3", "周三");
            put("4", "周四");
            put("5", "周五");
            put("6", "周六");
            put("7", "周日");
        }
    };

    private DateTimeUtils() {
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

    /** 根据数字返回“周几” */
    public static String getWeek(int weekValue) {
        return DateTimeUtils.WEEKDAY.get(weekValue + "");
    }

    public static LocalDate getMonday(LocalDate now) {
        return now.with(DayOfWeek.MONDAY);
    }

    public static LocalDateTime getMonday(LocalDateTime now) {
        return now.with(DayOfWeek.MONDAY);
    }

    public LocalDate getSunday(LocalDate now) {
        return now.with(DayOfWeek.SUNDAY);
    }

    public LocalDateTime getSunday(LocalDateTime now) {
        return now.with(DayOfWeek.SUNDAY);
    }

    /** 根据出生日期计算年龄 */
    public static int getAge(LocalDate birthday) {
        if (birthday == null) {
            return 0;
        }

        return birthday.until(LocalDate.now()).getYears();
    }

}
