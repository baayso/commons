package com.baayso.commons.exception;

import org.slf4j.Logger;

import com.baayso.commons.log.Log;
import com.baayso.commons.tool.BasicResponseStatus;
import com.baayso.commons.tool.ResponseStatus;

/**
 * 专用于 API 的异常。
 *
 * @author ChenFangjie (2017/2/25 18:37)
 * @since 1.0.0
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Log.get();

    private ResponseStatus responseStatus;

    public ApiException() {
    }

    public ApiException(String message) {
        this(BasicResponseStatus.SERVER_INTERNAL_ERROR.value(), message);
    }

    public ApiException(String message, Object... args) {
        this(String.format(message, args));
    }

    public ApiException(ResponseStatus responseStatus) {
        super(responseStatus.getReason());
        this.responseStatus = responseStatus;
    }

    public ApiException(int code, String message) {
        super(message);

        this.responseStatus = new ResponseStatus() {
            @Override
            public int value() {
                return code;
            }

            @Override
            public String getReason() {
                return message;
            }
        };
    }

    public ApiException(int code, String message, Object... args) {
        this(code, String.format(message, args));
    }

    public ApiException(ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public ApiException(ResponseStatus responseStatus, String message, Object... args) {
        super(String.format(message, args));
        this.responseStatus = responseStatus;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        if (log.isDebugEnabled()) {
            return super.fillInStackTrace();
        }

        return null;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}
