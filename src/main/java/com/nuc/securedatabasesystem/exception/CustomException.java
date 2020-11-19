package com.nuc.securedatabasesystem.exception;

import com.nuc.securedatabasesystem.exception.interfaces.BasicException;

/**
 * @Author DeepBlue
 * @Date 2020/11/11 19:40
 * 自定义异常类，对异常进行一个包装，这样的话开发更加方便
 */
public class CustomException extends RuntimeException implements BasicException {

    private String errorCode;
    private String errorMsg;

    public CustomException() {
        super();
    }

    public CustomException(BasicException exception) {
        super(exception.getResultCode());
        this.errorCode = exception.getResultCode();
        this.errorMsg = exception.getResultMsg();
    }

    public CustomException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CustomException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public CustomException(String errorCode, String errorMsg, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getResultCode() {
        return this.errorCode;
    }

    @Override
    public String getResultMsg() {
        return this.errorMsg;
    }
}
