package com.feng.community.storage;

import com.feng.community.helper.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.feng.community.constant.RedisKey.ZERO_COMMENT_POSTS;

/**
 * @author: fengyunan
 * Created on: 2021-05-04
 */
@Component
public class ZeroCommentPostCache {
    @Autowired
    private RedisHelper redisHelper;

    public void updateZeroCommentPosts(Long postId) {
        redisHelper.sSet(ZERO_COMMENT_POSTS.getPrefix(), postId);
    }

    public void delZeroCommentPosts(Object... values) {
        redisHelper.setRemove(ZERO_COMMENT_POSTS.getPrefix(), values);
    }

    public Set<Object> getZeroCommentPosts() {
        return redisHelper.sGet(ZERO_COMMENT_POSTS.getPrefix());
    }
}
