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

    NEED_LOGIN(1030, "需要登录哦！"), //需要登录
    ;

    private Integer code;
    private String msg;

    ResultViewCode(Integer code) {
        this.code = code;
    }

    ResultViewCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
