package com.feng.community.storage;

import com.feng.community.helper.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.feng.community.constant.RedisKey.HOT_TAGS;
import static com.feng.community.constant.RedisKey.TOP_POSTS;
import static com.feng.community.constant.StorageIndexEnum.*;

/**
 * @author: fengyunan
 * Created on: 2021-05-01
 */
@Component
public class TopPostsCache {
    @Autowired
    private RedisHelper redisHelper;

    public void updateTopPosts(Long postId, Long score) {
        redisHelper.addZSetValue(TOP_POSTS.getPrefix(), postId, score);
        // 裁剪4个
        checkTopPost();
    }

    public void delTopPost(Object... values) {
        redisHelper.removeZsetByValue(TOP_POSTS.getPrefix(), values);
    }

    public List<Object> getTopPosts() {
        List<Object> collect = redisHelper.getZSetRank(TOP_POSTS.getPrefix(), REDIS_START_INDEX.getIndex(), REDIS_END_INDEX.getIndex())
                .stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
        Collections.reverse(collect);
        return collect;
    }

    private void checkTopPost() {
        String key = TOP_POSTS.getPrefix();
        long zSetSize = redisHelper.getZSetSize(key);
        if (zSetSize > REDIS_TOP_POSTS_MAX.getIndex()) {
            redisHelper.removeZsetByRange(key, 0, zSetSize - REDIS_TOP_POSTS_MAX.getIndex());
        }
    }
}
