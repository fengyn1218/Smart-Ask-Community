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

    NEED_LOGIN(1030, "此操作需要登录哦，请点击右上角！"), //需要登录
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),

    USER_IS_EMPTY(2040, "该用户不存在"),

    QUESTION_NOT_FOUND(2001, "问题已经找不到了，已被删除或压根就不存在？"),

    REPEAT_LIKE(2023, "请不要重复收藏/点赞"),


    VALIDATE_ERROR(2060, "参数校验失败");

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
