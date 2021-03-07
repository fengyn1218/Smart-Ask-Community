package com.feng.community.constant;

import lombok.Data;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
public enum ResultViewCode {
    SUCCESS(200),
    FAIL(500)
    ;

    private Integer code;

    ResultViewCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
