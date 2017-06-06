package com.baayso.commons.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * 检验器。
 *
 * @author ChenFangjie (2017/6/2 10:25)
 * @since 1.0.0
 */
public class Validator {

    private static final String DEFAULT_DATE_PATTERN     = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 验证字符串是否为null或""。
     *
     * @param value 需要进行验证的字符串
     *
     * @return 当字符串不为null或""时返回 true
     *
     * @since 1.0.0
     */
    public boolean required(String value) {
        boolean result = true;

        if (StringUtils.isBlank(value)) {
            result = false;
        }

        return result;
    }

    /**
     * 验证整数。
     *
     * @param value 需要进行验证的字符串
     *
     * @return 此字符串是整数时返回 true
     *
     * @since 1.0.0
     */
    public boolean isInt(String value) {
        return this.isInt(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 验证整数。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小值
     * @param max   最大值
     *
     * @return 此字符串是整数并在指定的范围内返回 true
     *
     * @since 1.0.0
     */
    public boolean isInt(String value, int min, int max) {
        boolean result = true;

        if (!this.required(value)) {
            result = false;
        }
        else {
            try {
                int temp = Integer.parseInt(value);

                if (temp < min || temp > max) {
                    result = false;
                }
            }
            catch (Exception e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 验证长整数。
     *
     * @param value 需要进行验证的字符串
     *
     * @return 此字符串是长整数时返回 true
     *
     * @since 1.0.0
     */
    public boolean isLong(String value) {
        return this.isLong(value, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isLong(String value, long min, long max) {
        boolean result = true;

        if (!this.required(value)) {
            result = false;
        }
        else {
            try {
                long temp = Long.parseLong(value);

                if (temp < min || temp > max) {
                    result = false;
                }
            }
            catch (Exception e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 验证浮点数。
     *
     * @param value 需要进行验证的字符串
     *
     * @return 此字符串是浮点数时返回 true
     *
     * @since 1.0.0
     */
    public boolean isDouble(String value) {
        return this.isDouble(value, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    /**
     * 验证浮点数。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小值
     * @param max   最大值
     *
     * @return 此字符串是浮点数并在指定的范围内返回 true
     *
     * @since 1.0.0
     */
    public boolean isDouble(String value, double min, double max) {
        boolean result = true;

        if (!this.required(value)) {
            result = false;
        }
        else {
            try {
                double temp = Double.parseDouble(value);

                if (temp < min || temp > max) {
                    result = false;
                }
            }
            catch (Exception e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 验证给定的字符串是否为布尔值。
     *
     * @param value 需要进行验证的字符串
     *
     * @return 此字符串是布尔值时返回 true
     *
     * @since 1.0.0
     */
    public boolean isBoolean(String value) {
        boolean result = true;

        if (!this.required(value)) {
            result = false;
        }
        else {
            value = value.toLowerCase();

            if (!Objects.equals(Boolean.TRUE.toString(), value) && !Objects.equals(Boolean.FALSE.toString(), value)) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 验证日期（要求为：yyyy-MM-dd格式且必须是有效的日期）。
     *
     * @param value 需要进行验证的字符串
     *
     * @return 此字符串是日期时返回 true
     *
     * @since 1.0.0
     */
    public boolean isDate(String value) {
        return this.isDate(value, DEFAULT_DATE_PATTERN);
    }

    /**
     * 验证日期（要求为：yyyy-MM-dd格式且必须是有效的日期）。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小日期
     * @param max   最大日期
     *
     * @return 此字符串是日期并在指定的日期范围内返回 true
     *
     * @since 1.0.0
     */
    public boolean isDate(String value, Date min, Date max) {
        return this.isDate(value, min, max, DEFAULT_DATE_PATTERN);
    }

    /**
     * 验证日期（要求为：yyyy-MM-dd格式且必须是有效的日期）。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小日期
     * @param max   最大日期
     *
     * @return 此字符串是日期并在指定的日期范围内返回 true
     *
     * @since 1.0.0
     */
    public boolean isDate(String value, String min, String max) {
        boolean result = true;

        try {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

            result = isDate(value, format.parse(min), format.parse(max));
        }
        catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * 验证日期时间（要求为：yyyy-MM-dd HH:mm:ss 格式且必须是有效的日期时间）。
     *
     * @param value 需要进行验证的字符串
     *
     * @return 此字符串是日期时间时返回 true
     *
     * @since 1.0.0
     */
    public boolean isDateTime(String value) {
        return this.isDate(value, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 验证日期时间（要求为：yyyy-MM-dd HH:mm:ss 格式且必须是有效的日期时间）。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小日期时间
     * @param max   最大日期时间
     *
     * @return 此字符串是日期并在指定的日期时间范围内返回 true
     *
     * @since 1.0.0
     */
    public boolean isDateTime(String value, Date min, Date max) {
        return this.isDate(value, min, max, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 验证日期时间（要求为：yyyy-MM-dd HH:mm:ss 格式且必须是有效的日期时间）。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小日期时间
     * @param max   最大日期时间
     *
     * @return 此字符串是日期并在指定的日期时间范围内返回 true
     *
     * @since 1.0.0
     */
    public boolean isDateTime(String value, String min, String max) {
        boolean result = true;

        try {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);

            result = isDateTime(value, format.parse(min), format.parse(max));
        }
        catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * 验证日期。
     *
     * @param value   需要进行验证的字符串
     * @param pattern 日期格式
     *
     * @return 此字符串是日期时返回 true
     *
     * @since 1.0.0
     */
    public boolean isDate(String value, String pattern) {
        boolean result = true;

        if (!this.required(value)) {
            result = false;
        }
        else {
            try {
                new SimpleDateFormat(pattern).parse(value);
            }
            catch (Exception e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 验证日期时间。
     *
     * @param value   需要进行验证的字符串
     * @param min     最小日期时间
     * @param max     最大日期时间
     * @param pattern 日期格式
     *
     * @return 此字符串是日期并在指定的日期时间范围内返回 true
     *
     * @since 1.0.0
     */
    public boolean isDate(String value, Date min, Date max, String pattern) {
        boolean result = true;

        if (!this.required(value)) {
            result = false;
        }
        else {
            try {
                Date temp = new SimpleDateFormat(pattern).parse(value);

                if (temp.before(min) || temp.after(max)) {
                    result = false;
                }
            }
            catch (Exception e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 验证给定的字符串是否匹配给定的正则表达式。
     *
     * @param str   需要进行验证的字符串，传入null值将返回 false
     * @param regex 用来匹配此字符串的正则表达式
     *
     * @return 此字符串匹配给定的正则表达式时返回 true
     *
     * @since 1.0.0
     */
    public boolean validateByRegex(String str, String regex) {
        return StringUtils.isNotBlank(str) && str.matches(regex);
    }

    /**
     * 验证给定的字符串是否匹配给定的正则表达式。
     *
     * @param str   需要进行验证的字符串，传入null值将返回 false
     * @param regex 用来匹配此字符串的正则表达式
     * @param min   最小长度
     * @param max   最大长度
     *
     * @return 此字符串匹配给定的正则表达式并且符合长度要求时返回 true
     *
     * @since 1.0.0
     */
    public boolean validateByRegex(String str, String regex, int min, int max) {
        boolean result = true;

        if (!this.validateByRegex(str, regex)) {
            result = false;
        }
        else if (StringUtils.length(str) < min || StringUtils.length(str) > max) {
            result = false;
        }

        return result;
    }

    /**
     * 验证给定的字符串是否为：中文、字母和数字。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小长度
     * @param max   最大长度
     *
     * @return 此字符串满足要求并且符合长度要求时返回 true
     *
     * @since 1.0.0
     */
    public boolean isChineseAndEnglishAndNumber(String value, int min, int max) {
        String regex = "^[a-zA-Z0-9\\u4e00-\\u9fa5]*$";

        return this.validateByRegex(value, regex, min, max);
    }

    /**
     * 验证给定的字符串是否为：字母、数字和下划线。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小长度
     * @param max   最大长度
     *
     * @return 此字符串满足要求并且符合长度要求时返回 true
     *
     * @since 1.0.0
     */
    public boolean isEnglishAndNumberAndUnderscore(String value, int min, int max) {
        String regex = "^[a-zA-Z0-9_]*$";

        return this.validateByRegex(value, regex, min, max);
    }

    /**
     * 验证给定的字符串是否为：字母和数字。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小长度
     * @param max   最大长度
     *
     * @return 此字符串满足要求并且符合长度要求时返回 true
     *
     * @since 1.0.0
     */
    public boolean isEnglishAndNumber(String value, int min, int max) {
        String regex = "^[a-zA-Z0-9]*$";

        return this.validateByRegex(value, regex, min, max);
    }

    /**
     * 验证给定的字符串是否为：数字和中划线。
     *
     * @param value 需要进行验证的字符串
     * @param min   最小长度
     * @param max   最大长度
     *
     * @return 此字符串满足要求并且符合长度要求时返回 true
     *
     * @since 1.0.0
     */
    public boolean isNumberAndHyphen(String value, int min, int max) {
        String regex = "^[-0-9]*$";

        return this.validateByRegex(value, regex, min, max);
    }

    /**
     * 验证给定的字符串是否为电子邮件地址。
     *
     * @param email 需要进行验证的字符串
     *
     * @return 此字符串是Email时返回 true
     *
     * @since 1.0.0
     */
    public boolean isEmail(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

        return this.validateByRegex(email, regex);
    }

    /**
     * 验证姓名。 <br/>
     * 1、可以是汉字或字母，但是不能两者都有； <br/>
     * 2、不能包含任何符号和数字； <br/>
     * 3、允许英文名字中出现空格，但是不能连续出现多个； <br/>
     * 4、中文名不能出现空格。
     *
     * @param name 需要进行验证的字符串
     *
     * @return 当且仅当此字符串满足要求时返回 true
     *
     * @since 1.0.0
     */
    public boolean isName(String name) {
        String regex = "^([\\u4e00-\\u9fa5]+|([a-zA-Z]+\\s?)+)$";

        return this.validateByRegex(name, regex);
    }

    /**
     * 验证给定的字符串是否为URL。
     *
     * @param url 需要进行验证的字符串
     *
     * @return 此字符串是URL时返回 true
     *
     * @since 1.0.0
     */
    public boolean isURL(String url) {
        boolean result = true;

        if (!this.required(url)) {
            result = false;
        }
        else {
            try {
                if (url.startsWith("https://")) {
                    url = "http://" + url.substring(8); // URL doesn't understand the https protocol, hack it
                }

                new URL(url);
            }
            catch (MalformedURLException e) {
                result = false;
            }
        }

        return result;
    }

}
