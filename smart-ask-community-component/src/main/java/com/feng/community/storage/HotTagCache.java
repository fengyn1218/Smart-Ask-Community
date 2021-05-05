package com.feng.community.storage;

import com.feng.community.helper.RedisHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.feng.community.constant.RedisKey.HOT_TAGS;
import static com.feng.community.constant.StorageIndexEnum.*;

/**
 * @author: fengyunan
 * Created on: 2021-04-29
 */
@Data
@Component
public class HotTagCache {

    @Autowired
    private RedisHelper redisHelper;

    public void updateHotTag(Map<String, Integer> map) {
        map.forEach((key, value) -> redisHelper.addZSetValue(HOT_TAGS.getPrefix(), key, value));
        // 裁剪15个
        checkHotTag();
    }

    public void delHotTag(Object... values) {
        redisHelper.removeZsetByValue(HOT_TAGS.getPrefix(), values);
    }

    public Map<Object, Object> getHotTag() {
        return redisHelper.getZSetRank(HOT_TAGS.getPrefix(), REDIS_START_INDEX.getIndex(), REDIS_END_INDEX.getIndex())
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue,
                        ZSetOperations.TypedTuple::getScore,
                        (o, n) -> n));
    }

    private void checkHotTag() {
        String key = HOT_TAGS.getPrefix();
        long zSetSize = redisHelper.getZSetSize(key);
        if (zSetSize > REDIS_ZSET_MAX.getIndex()) {
            redisHelper.removeZsetByRange(key, 0, zSetSize - REDIS_ZSET_MAX.getIndex());
        }
    }

}
