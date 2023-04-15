package com.niu.web.business.businessException;

/**
 * @author niushuanglong
 * @date 2023/3/9 10:40:37
 * @description 验证码异常
 */
public class CodeException extends Exception{
    public CodeException() {
        super();
    }

    public CodeException(String message) {
        super(message);
    }

    public CodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeException(Throwable cause) {
        super(cause);
    }

    protected CodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
