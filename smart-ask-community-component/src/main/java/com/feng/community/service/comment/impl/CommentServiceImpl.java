package com.feng.community.service.comment.impl;

import com.feng.community.dao.TbCommentMapper;
import com.feng.community.dto.CommentDTO;
import com.feng.community.dto.CommentQueryDTO;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.entity.TbComment;
import com.feng.community.service.comment.CommentService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: fengyunan
 * Created on: 2021-04-09
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private TbCommentMapper tbCommentMapper;

    @Override
    public PaginationDTO<CommentDTO> getCommentList(CommentQueryDTO commentQueryDTO) {
        Integer totalPage;
        Integer totalCount = commentExtMapper.countBySearch(commentQueryDTO);
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        if (commentQueryDTO.getCommentator() != null)
            criteria.andCommentatorEqualTo(commentQueryDTO.getCommentator());
        if (commentQueryDTO.getId() != null)
            criteria.andIdEqualTo(commentQueryDTO.getId());
        if (commentQueryDTO.getParentId() != null)
            criteria.andParentIdEqualTo(commentQueryDTO.getParentId());
        if (commentQueryDTO.getType() != null)
            criteria.andTypeEqualTo(commentQueryDTO.getType());

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

        commentExample.setOrderByClause(commentQueryDTO.getSort() + " " + commentQueryDTO.getOrder());
        //commentExample.setOrderByClause("gmt_modified desc");
        List<Comment> comments = commentMapper.selectByExampleWithRowbounds(commentExample, new RowBounds(commentQueryDTO.getSize() * (commentQueryDTO.getPage() - 1), commentQueryDTO.getSize()));
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalCount(totalCount);
        if (comments.size() == 0) {
            paginationDTO.setPage(0);
            paginationDTO.setTotalPage(0);
            paginationDTO.setData(new ArrayList<>());
            return paginationDTO;
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);


        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userMap.get(comment.getCommentator()), userDTO);
            commentDTO.setUser(userService.getUserDTO(userDTO.getId()));
           /* UserAccountExample userAccountExample = new UserAccountExample();
            userAccountExample.createCriteria().andUserIdEqualTo(userMap.get(comment.getCommentator()).getId());
            List<UserAccount> userAccounts = userAccountMapper.selectByExample(userAccountExample);
            commentDTO.setUserAccount(userAccounts.get(0));*/
            commentDTO.setGmtModifiedStr(timeUtils.getTime(commentDTO.getGmtModified(), null));
            return commentDTO;
        }).collect(Collectors.toList());


        paginationDTO.setData(commentDTOS);
        paginationDTO.setPagination(totalPage, commentQueryDTO.getPage());
        return paginationDTO;
    }
}
