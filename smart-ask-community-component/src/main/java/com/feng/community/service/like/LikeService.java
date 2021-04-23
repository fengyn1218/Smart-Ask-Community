package com.feng.community.service.like;

import com.feng.community.entity.TbUser;

public interface LikeService {
    boolean isLiked(TbUser user, Long postId);
}
