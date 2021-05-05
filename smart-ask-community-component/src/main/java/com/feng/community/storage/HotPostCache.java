package com.feng.community.storage;

import com.feng.community.helper.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.feng.community.constant.RedisKey.HOT_MONTH_RANK;
import static com.feng.community.constant.RedisKey.HOT_WEEK_RANK;
import static com.feng.community.constant.StorageIndexEnum.REDIS_END_INDEX;
import static com.feng.community.constant.StorageIndexEnum.REDIS_START_INDEX;

/**
 * @author: fengyunan
 * Created on: 2021-05-04
 */
@Component
public class HotPostCache {
    @Autowired
    private RedisHelper redisHelper;

    public void updateHot7Posts(Map<Long, Long> map) {
        // 周榜
        for (Map.Entry<Long, Long> longLongEntry : map.entrySet()) {
            redisHelper.addZSetValue(HOT_WEEK_RANK.getPrefix(), longLongEntry.getKey(), longLongEntry.getValue());
        }
        // 有效期为1小时
        redisHelper.expire(HOT_WEEK_RANK.getPrefix(), 1000 * 60 * 60 - 10);
    }

    public List<Object> getHot7Posts() {
        List<Object> collect = redisHelper.getZSetRank(HOT_WEEK_RANK.getPrefix(), REDIS_START_INDEX.getIndex(), REDIS_END_INDEX.getIndex())
                .stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
        Collections.reverse(collect);
        return collect;
    }

    public void updateHot30Posts(Map<Long, Long> map) {
        // 月榜
        for (Map.Entry<Long, Long> longLongEntry : map.entrySet()) {
            redisHelper.addZSetValue(HOT_MONTH_RANK.getPrefix(), longLongEntry.getKey(), longLongEntry.getValue());
        }
        // 有效期1小时
        redisHelper.expire(HOT_MONTH_RANK.getPrefix(), 1000 * 60 * 60 - 30);
    }

    public List<Object> getHot30Posts() {
        List<Object> collect = redisHelper.getZSetRank(HOT_MONTH_RANK.getPrefix(), REDIS_START_INDEX.getIndex(), REDIS_END_INDEX.getIndex())
                .stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
        Collections.reverse(collect);
        return collect;
    }

}
