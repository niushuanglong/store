package com.niu.web.business.businessException;

/**
 * @author niushuanglong
 * @date 2023/4/13 21:32:14
 * @description
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 3236030598595317425L;

    private String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage(){
        String message = super.getMessage();
        return message;
    }

    public String getOriMsg(){
        return super.getMessage();
    }
}

