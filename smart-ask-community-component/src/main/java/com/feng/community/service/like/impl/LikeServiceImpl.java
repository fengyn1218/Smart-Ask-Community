package com.feng.community.service.like.impl;

import com.feng.community.dao.TbCommentMapper;
import com.feng.community.dao.TbLikeMapper;
import com.feng.community.dao.TbPostMapper;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbComment;
import com.feng.community.entity.TbLike;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.service.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

import static com.feng.community.constant.LikeConstant.*;
import static com.feng.community.constant.ResultViewCode.REPEAT_LIKE;

/**
 * @author: fengyunan
 * Created on: 2021-04-21
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private TbLikeMapper tbLikeMapper;
    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private TbCommentMapper tbCommentMapper;

    @Override
    public boolean isLiked(TbUser user, Long postId) {
        Example example = new Example(TbLike.class);
        example.createCriteria().andEqualTo("liker", user.getId());
        List<TbLike> tbLikes = tbLikeMapper.selectByExample(example);
        if (tbLikes.size() != 0) {
            for (int i = 0; i < tbLikes.size(); i++) {
                if (tbLikes.get(i).getTargetId().equals(postId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ResultView likePost(TbUser user, Long postId) {
        // 是否已经收藏
        if (isLiked(user, postId)) {
            return new ResultView(REPEAT_LIKE.getCode(), REPEAT_LIKE.getMsg());
        }
        TbLike like = new TbLike();
        like.setTargetId(postId);
        like.setType(TYPE_COLLECTION);
        like.setLiker(user.getId());
        like.setCreated(System.currentTimeMillis());

        int insert = tbLikeMapper.insert(like);
        if (insert == 1) {
            // 收藏数加一
            TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
            tbPost.setLikeCount(tbPost.getLikeCount() + LIKE_STEP);
            tbPostMapper.updateByPrimaryKey(tbPost);

            return ResultView.success("恭喜您，收藏成功！");
        } else {
            return ResultView.fail("收藏失败了，请稍后重试");
        }
    }

    @Override
    public ResultView likeComment(TbUser user, Long commentId) {
        // 是否已经收藏
        if (isLiked(user, commentId)) {
            return new ResultView(REPEAT_LIKE.getCode(), REPEAT_LIKE.getMsg());
        }
        TbLike like = new TbLike();
        like.setTargetId(commentId);
        like.setType(TYPE_LIKE);
        like.setLiker(user.getId());
        like.setCreated(System.currentTimeMillis());
        int insert = tbLikeMapper.insert(like);

        if (insert == 1) {
            // 点赞数加一
            TbComment comment = tbCommentMapper.selectByPrimaryKey(commentId);
            comment.setLikeCount(comment.getLikeCount() + LIKE_STEP);
            tbCommentMapper.updateByPrimaryKey(comment);

            return ResultView.success("点赞成功！");
        } else {
            return ResultView.fail("点赞失败了，请稍后重试");
        }
    }

    @Override
    public ResultView cancel(TbUser user, Long postId) {
        Example example = new Example(TbLike.class);
        example.createCriteria()
                .andEqualTo("liker", user.getId())
                .andEqualTo("targetId", postId);
        int i = tbLikeMapper.deleteByExample(example);
        if (i == 1) {
            // 收藏数减一
            TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
            tbPost.setLikeCount(tbPost.getLikeCount() - LIKE_STEP);
            tbPostMapper.updateByPrimaryKey(tbPost);
            return ResultView.success("已经取消收藏该帖子！");
        } else {
            return ResultView.fail("取消收藏失败！");
        }
    }
}
