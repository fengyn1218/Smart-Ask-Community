package com.feng.community.constant;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
public class PostConstant {
    // 观看人数增加频率
    public static final int VIEW_COUNT_STEP = 1;

    // 排序规则
    public static final String HOT30 = "hot30";
    public static final String HOT7 = "hot7";
    public static final String NO = "no";
    public static final String NEW = "new";
    public static final String GOOD = "good";

    /**
     * 所属专栏 ：
     * 1：提问
     * 2：分享
     * 3：讨论
     * 4：建议
     * 5:公告
     * 6:动态
     */
    public static final Long TYPE_ASK = 1L;
    public static final Long TYPE_SHARE = 2L;
    public static final Long TYPE_DISCUSSION = 3L;
    public static final Long TYPE_SUGGESTIONS = 4L;
    public static final Long TYPE_ANNOUNCEMENT = 5L;
    public static final Long TYPE_NEW = 6L;

}
