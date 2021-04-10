package com.feng.community.service.comment.impl;

import com.feng.community.dao.TbCommentMapper;
import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.CommentDTO;
import com.feng.community.dto.CommentQueryDTO;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.entity.TbComment;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.service.comment.CommentService;
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
    private UserInfoService userInfoService;

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
        List<TbComment> tbComments = tbCommentMapper.selectByExampleAndRowBounds(example, new RowBounds(commentQueryDTO.getSize() * (commentQueryDTO.getPage() - 1), commentQueryDTO.getSize()));

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
        List<CommentDTO> commentDTOS = tbComments.stream().map(comment -> {
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
}
