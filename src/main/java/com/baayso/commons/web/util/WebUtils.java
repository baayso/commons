package com.baayso.commons.web.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Miscellaneous utilities for web applications.
 *
 * @author ChenFangjie (2015/11/30 12:59)
 * @since 1.0.0
 */
public final class WebUtils {

    // 让工具类彻底不可以实例化
    private WebUtils() {
        throw new Error("工具类不可以实例化！");
    }

    /**
     * 获取 HTTP 请求。
     *
     * @return {@linkplain HttpServletRequest}
     *
     * @since 1.0.0
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        }

        return request;
    }

    /**
     * 获取 HTTP 响应。
     *
     * @return {@linkplain HttpServletResponse}
     *
     * @since 1.0.0
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = null;

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            response = ((ServletRequestAttributes) requestAttributes).getResponse();
        }

        return response;
    }

    /**
     * 获取 HTTP Session。
     *
     * @return {@linkplain HttpSession}
     *
     * @since 1.0.0
     */
    public static HttpSession getSession() {
        HttpSession session = null;

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession(false);
        }

        return session;
    }

    /**
     * 获取当前 request 的 session id。
     *
     * @return session id, 如果 session 为空则返回 {@code null}
     *
     * @see org.springframework.web.util.WebUtils#getSessionId(HttpServletRequest)
     * @since 1.0.0
     */
    public static String getSessionId() {
        return org.springframework.web.util.WebUtils.getSessionId(getRequest());
    }

    /**
     * 将具有给定名称的会话属性设置为给定值。
     * 如果值为 {@code null}（如果会话存在），则删除会话属性。
     * 如果没有必要，则不创建新会话！
     *
     * @param name  会话属性的名称
     * @param value 会话属性的值
     *
     * @see org.springframework.web.util.WebUtils#setSessionAttribute(HttpServletRequest, String, Object)
     * @since 1.0.0
     */
    public static void setSessionAttribute(String name, Object value) {
        org.springframework.web.util.WebUtils.setSessionAttribute(getRequest(), name, value);
    }

    /**
     * 检查给定请求中给定名称的会话属性。
     * 如果没有会话或会话没有此类属性，则返回 {@code null}。
     * 如果以前不存在新会话，则不会创建新会话！
     *
     * @param name 会话属性的名称
     *
     * @return 会话属性的值，如果未找到则返回 {@code null}
     *
     * @see org.springframework.web.util.WebUtils#getSessionAttribute(HttpServletRequest, String)
     * @since 1.0.0
     */
    public static Object getSessionAttribute(String name) {
        return org.springframework.web.util.WebUtils.getSessionAttribute(getRequest(), name);
    }

    /**
     * 获取本地化，从HttpServletRequest中获取，没有则返回Locale.SIMPLIFIED_CHINESE
     *
     * @return {@link java.util.Locale}
     *
     * @since 1.0.0
     */
    public static Locale getLocal() {
        HttpServletRequest request = getRequest();

        if (request == null) {
            return Locale.SIMPLIFIED_CHINESE;
        }

        return request.getLocale();
    }

    /**
     * 获取客户端IP地址。
     *
     * @param request {@linkplain javax.servlet.http.HttpServletRequest}
     *
     * @return 客户端IP
     *
     * @since 1.0.0
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "-";
        }

        String ip = request.getHeader("X-Real-IP");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip) ? "127.0.0.1" : ip;
    }

}
