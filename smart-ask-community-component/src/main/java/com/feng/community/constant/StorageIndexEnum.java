package com.feng.community.constant;

/**
 * redis范围查询时索引
 *
 * @author: fengyunan
 * Created on: 2021-04-29
 */
public enum StorageIndexEnum {
    REDIS_START_INDEX(0),
    REDIS_END_INDEX(-1),
    REDIS_TOP_POSTS_MAX(4),
    REDIS_ZSET_MAX(15),
    ;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    StorageIndexEnum(Integer index) {
        this.index = index;
    }

    private Integer index;
}
