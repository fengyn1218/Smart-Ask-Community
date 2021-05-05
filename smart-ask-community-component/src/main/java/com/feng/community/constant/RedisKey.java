package com.feng.community.constant;

/**
 * <p>
 * Redis key 所有key在此统一管理
 * prefix 业务分类前缀
 * <p>
 *
 * @author fengyunan
 * Created on 2021-03-08
 */
public enum RedisKey {

    SEND_MAIL_CODE("send-mail-code"), // 邮箱验证码缓存

    LOGIN_USERS("login-user"), // 最近登录用户缓存
    HOT_TAGS("hot-tags"), // 热门标签缓存

    TOP_POSTS("top-posts"), // 首页topPosts缓存
    HOT_WEEK_RANK("hot-7-rank"), // 周榜帖子
    HOT_MONTH_RANK("hot-30-rank"), // 月榜帖子

    ZERO_COMMENT_POSTS("zero-comment-posts"), //0评论帖子 (用作抢沙发)

    TAGS_COMMON("tags-common"), // 常用标签缓存
    TAGS_PROGRAM("tags-program"), // 语言标签缓存
    TAGS_FRAMEWORK("tags-framework"), // 标签框架缓存
    TAGS_TOOL("tags-tool"), // 工具标签缓存
    TAGS_DB("tags-db"), // 数据库标签缓存
    TAGS_OTHER("tags-other"), // 其他标签缓存
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
