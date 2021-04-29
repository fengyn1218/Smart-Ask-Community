package com.feng.community.service.comment.impl;

import com.feng.community.dao.TbCommentMapper;
import com.feng.community.dao.TbLikeMapper;
import com.feng.community.dao.TbPostMapper;
import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.CommentDTO;
import com.feng.community.dto.CommentQueryDTO;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbComment;
import com.feng.community.entity.TbLike;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.service.comment.CommentService;
import com.feng.community.service.post.PostService;
import com.feng.community.service.user.UserInfoService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.feng.community.constant.CommentConstant.*;
import static com.feng.community.constant.TimeConstant.FORMAT;

/**
 * @author: fengyunan
 * Created on: 2021-04-09
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private TbCommentMapper tbCommentMapper;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private TbLikeMapper tbLikeMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PostService postService;

    @Override
    public PaginationDTO<CommentDTO> getCommentList(CommentQueryDTO commentQueryDTO) {
        Integer totalPage;
        Example example = new Example(TbComment.class);
        example.createCriteria().andEqualTo("authorId", commentQueryDTO.getAuthorId());
        // 获取评论总数
        Integer totalCount = tbCommentMapper.selectCountByExample(example);

        Example.Criteria criteria = example.createCriteria();

        if (commentQueryDTO.getAuthorId() != null)
            criteria.andEqualTo("authorId", commentQueryDTO.getAuthorId());
        if (commentQueryDTO.getId() != null)
            criteria.andEqualTo("id", commentQueryDTO.getId());
        if (commentQueryDTO.getPostId() != null)
            criteria.andEqualTo("postId", commentQueryDTO.getPostId());
        if (commentQueryDTO.getType() != null)
            criteria.andEqualTo("type", commentQueryDTO.getType());

        if (totalCount % commentQueryDTO.getSize() == 0) {
            totalPage = totalCount / commentQueryDTO.getSize();
        } else {
            totalPage = totalCount / commentQueryDTO.getSize() + 1;
        }

        if (commentQueryDTO.getPage() > totalPage) {
            commentQueryDTO.setPage(totalPage);
        }

        Integer offset = commentQueryDTO.getPage() < 1 ? 0 : commentQueryDTO.getSize() * (commentQueryDTO.getPage() - 1);
        commentQueryDTO.setOffset(offset);

        example.setOrderByClause(commentQueryDTO.getSort() + " " + commentQueryDTO.getOrder());
        List<TbComment> tbComments = tbCommentMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, commentQueryDTO.getSize()));

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalCount(totalCount);
        if (tbComments.size() == 0) {
            paginationDTO.setPage(0);
            paginationDTO.setTotalPage(0);
            paginationDTO.setData(new ArrayList<>());
            return paginationDTO;
        }
        // 去重评论作者
        Set<Long> commentators = tbComments.stream().map(TbComment::getAuthorId).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>(commentators);

        // 获取评论人并转换为 Map
        Example userExample = new Example(TbUser.class);
        userExample.createCriteria().andEqualTo("id", userIds);
        List<TbUser> users = tbUserMapper.selectByExample(userExample);
        Map<Long, TbUser> userMap = users.stream().collect(Collectors.toMap(TbUser::getId, user -> user));
        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = tbComments
                .stream()
                .map(
                        comment -> {
                            CommentDTO commentDTO = new CommentDTO();
                            BeanUtils.copyProperties(comment, commentDTO);
                            TbUser tbUser = new TbUser();
                            BeanUtils.copyProperties(userMap.get(comment.getAuthorId()), tbUser);
                            commentDTO.setUser(userInfoService.selectUserByUserId(String.valueOf(tbUser.getId())));
                            commentDTO.setCreatedStr(DateFormatUtils.format(commentDTO.getCreated(), FORMAT));
                            return commentDTO;
                        }).collect(Collectors.toList());
        paginationDTO.setData(commentDTOS);
        paginationDTO.setPagination(totalPage, commentQueryDTO.getPage());
        return paginationDTO;
    }

    @Override
    public List<TbComment> getCommentByPostId(Long postId) {
        Example example = new Example(TbComment.class);
        example.createCriteria().andEqualTo("postId", postId);
        return tbCommentMapper.selectByExample(example);
    }

    @Override
    public ResultView publish(Long postId, Long userId, String content, boolean isReComment) {
        TbComment comment = new TbComment();
        comment.setAuthorId(userId);
        comment.setContent(content);
        comment.setPostId(postId);
        comment.setCreated(System.currentTimeMillis());
        comment.setCommentCount(COMMENT_LIKE_COUNT);
        // 单独处理二级评论
        if (isReComment) {
            comment.setType(RE_COMMENT_TYPE);
        } else {
            comment.setType(postService.getPostTypeById(postId));
        }
        comment.setLikeCount(DEFAULT_COMMENT_LIKE_COUNT);

        int insert = tbCommentMapper.insert(comment);
        if (insert == 1) {
            if (isReComment) {
                // 评论数加一
                TbComment comment1 = tbCommentMapper.selectByPrimaryKey(postId);
                comment1.setCommentCount(comment1.getCommentCount() + COMMENT_STEP);
                tbCommentMapper.updateByPrimaryKey(comment1);
            } else {
                // 评论数加一
                TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
                tbPost.setCommentCount(tbPost.getCommentCount() + COMMENT_STEP);
                tbPostMapper.updateByPrimaryKey(tbPost);
            }

            return ResultView.success("评论成功！");
        } else {
            return ResultView.fail("呀！评论失败了，请稍后后重试");
        }
    }

    @Override
    public ResultView delete(Long postId, Long userId, Long commentId) {
        TbComment comment = tbCommentMapper.selectByPrimaryKey(commentId);
        TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
        // 帖子作者/评论作者可以删除评论
        if (comment.getAuthorId().equals(userId) || tbPost.getAuthorId().equals(userId)) {
            int i = tbCommentMapper.deleteByPrimaryKey(comment);
            if (i == 1) {
                // 评论数减一
                tbPost.setCommentCount(tbPost.getCommentCount() - COMMENT_STEP);
                tbPostMapper.updateByPrimaryKey(tbPost);
                // 删除二级评论
                Example example = new Example(TbComment.class);
                example.createCriteria().andEqualTo("postId", commentId);
                tbCommentMapper.deleteByExample(example);
                // 删除点赞信息
                Example example1 = new Example(TbLike.class);
                example1.createCriteria().andEqualTo("targetId", comment.getId());
                tbLikeMapper.deleteByExample(example1);
                return ResultView.success("删除成功！");
            } else {
                return ResultView.fail("删除失败，请重试！");
            }
        } else {
            return ResultView.fail("您无法删除该评论，没有权限！");
        }
    }

    @Override
    public List<CommentDTO> listByTargetId(Long id) {
        Example example = new Example(TbComment.class);
        example.createCriteria().andEqualTo("postId", id);
        List<TbComment> tbComments = tbCommentMapper.selectByExample(example);

        if (tbComments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = tbComments.stream().map(TbComment::getAuthorId).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);

        // 获取评论人并转换为 Map
        Example example1 = new Example(TbUser.class);
        example1.createCriteria().andIn("id", userIds);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example1);

        Map<Long, TbUser> userMap = tbUsers.stream().collect(Collectors.toMap(TbUser::getId, user -> user));
        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = tbComments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);

            commentDTO.setUser(userInfoService.selectUserByUserId(String.valueOf(comment.getAuthorId())));
            commentDTO.setCreatedStr(DateFormatUtils.format(comment.getCreated(), FORMAT));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
