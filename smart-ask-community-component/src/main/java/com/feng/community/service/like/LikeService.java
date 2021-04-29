package com.feng.community.service.like;

import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;

public interface LikeService {
    // 判断是否点赞或者收藏
    boolean isLiked(TbUser user, Long postId);

    // 收藏帖子
    ResultView likePost(TbUser user, Long postId);

    //点赞评论
    ResultView likeComment(TbUser user, Long commentId);

    // 取消收藏/点赞
    ResultView cancel(TbUser user, Long postId);
}
