package com.feng.community.constant;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
public enum ResultViewCode {
    SUCCESS(200),
    FAIL(500),
    SEND_MAIL_FAIL(1024), //发送邮件失败
    SEND_MAIL_SUCCESS(1025), //发送邮件成功
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
