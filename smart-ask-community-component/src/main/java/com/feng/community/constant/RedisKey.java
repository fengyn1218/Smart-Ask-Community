package com.feng.community.constant;

/**
 * Redis key 所有key在此统一管理
 *
 * prefix 业务分类前缀
 *
 * @author fengyunan
 * Created on 2021-03-08
 */
public enum RedisKey {

    SEND_MAIL_CODE("send-mail-code"), // 邮箱验证码缓存
    LOGIN_USERS("login-user"), // 最近登录用户缓存
    ;


    private String prefix;

    public String of(String string) {
        return prefix + "-" + string;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    RedisKey(String prefix) {
        this.prefix = prefix;
    }
}
