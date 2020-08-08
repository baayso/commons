package com.baayso.commons.interceptor;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baayso.commons.exception.ApiException;
import com.baayso.commons.tool.BasicResponseStatus;
import com.baayso.commons.web.BodyReaderHttpServletRequestWrapper;
import com.vip.vjtools.vjkit.text.EncodeUtil;

/**
 * 验证数据摘要拦截器。
 *
 * @author ChenFangjie (2017/2/25 18:23)
 * @since 1.0.0
 */
public abstract class DataDigestInterceptorAdapter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 包装HttpServletRequest，防止流读取一次后就没有了
        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);

        String dataToken = requestWrapper.getParameter("dataToken");

        if (StringUtils.isAnyEmpty(dataToken)) {
            throw new ApiException(BasicResponseStatus.DATA_TOKEN_MISSING);
        }

        String requestBody = StreamUtils.copyToString(requestWrapper.getInputStream(), StandardCharsets.UTF_8);

        String data = requestBody + this.getSalt();

        byte[] digest = DigestUtils.sha1(data);

        if (!StringUtils.equalsIgnoreCase(dataToken, EncodeUtil.encodeHex(digest))) {
            throw new ApiException(BasicResponseStatus.DATA_TOKEN_CHECK_FAILED);
        }

        return super.preHandle(requestWrapper, response, handler);
    }

    /**
     * 获取生成摘要时所使用的盐，由子类提供。
     *
     * @return 生成摘要时所使用的盐
     *
     * @since 1.0.0
     */
    protected abstract String getSalt();

}
