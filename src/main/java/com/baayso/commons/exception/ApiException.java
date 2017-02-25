package com.baayso.commons.exception;

import org.slf4j.Logger;

import com.baayso.commons.log.Log;
import com.baayso.commons.tool.ResponseStatus;

/**
 * 专用于 API 的异常。
 *
 * @author ChenFangjie (2017/2/25 18:37)
 * @since 1.0.0
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -3247721709918992766L;

    private static final Logger log = Log.get();

    public ResponseStatus responseStatus;

    public ApiException() {
    }

    public ApiException(ResponseStatus responseStatus) {
        super(responseStatus.getReason());
        this.responseStatus = responseStatus;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        if (log.isDebugEnabled()) {
            return super.fillInStackTrace();
        }

        return null;
    }

}
