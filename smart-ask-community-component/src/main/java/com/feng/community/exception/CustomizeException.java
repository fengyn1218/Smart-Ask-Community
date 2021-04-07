package com.feng.community.exception;

import com.feng.community.constant.ResultViewCode;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ResultViewCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
