package com.feng.community.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Redis key 所有key在此统一管理
 *
 * @author fengyunan
 * Created on 2021-03-08
 */
public enum RedisKey {

    SEND_MAIL_CODE("send-mail-code"), // 邮箱验证码缓存
    ;

    private List<String> list = Arrays.stream(values()).map(Enum::name).collect(Collectors.toList());


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
