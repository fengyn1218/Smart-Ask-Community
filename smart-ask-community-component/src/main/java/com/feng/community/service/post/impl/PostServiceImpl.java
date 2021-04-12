package com.feng.community.service.post.impl;

import com.feng.community.constant.PostConstant;
import com.feng.community.dao.TbPostMapper;
import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.CommentDTO;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.dto.PostDTO;
import com.feng.community.entity.TbComment;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.feng.community.constant.PostConstant.VIEW_COUNT_STEP;
import static com.feng.community.constant.TimeConstant.FORMAT;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public List<TbPost> getTopPost(String search, String tag, String sort, Integer type) {
        Example example = new Example(TbPost.class);
        example.createCriteria().andEqualTo("type", type);
        List<TbPost> tbPosts = tbPostMapper.selectByExample(example);
        return tbPosts;
    }

    @Override
    public PaginationDTO getPostByType(String search, String tag, String sort, Integer page, Integer size, Integer type) {
        PaginationDTO<PostDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;
        Example example = new Example(TbPost.class);
        example.createCriteria().andEqualTo("type", type);
        int totalCount = tbPostMapper.selectCountByExample(example);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        //   Example example = new Example(TbPost.class);
        //  example.createCriteria().andEqualTo("type", type);
        List<TbPost> tbPosts = tbPostMapper.selectByExample(example);

        List<PostDTO> postDTOList = tbPosts.stream().map(e -> {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(e, postDTO);

            TbUser tbUser = tbUserMapper.selectByPrimaryKey(e.getAuthorId());


            postDTO.setUser(tbUser);
            return postDTO;
        }).collect(Collectors.toList());

        paginationDTO.setData(postDTOList);


        paginationDTO.setPagination(totalPage, page);

        return paginationDTO;
    }

    @Override
    public PaginationDTO<TbPost> listByUserId(Long userId, Integer page, Integer size) {
        Integer totalPage;
        Example example = new Example(TbPost.class);
        example.createCriteria().andEqualTo("authorId", userId);
        Integer totalCount = (int) tbPostMapper.selectCountByExample(example);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        Integer offset = size * (page - 1);
        example.setOrderByClause("updated desc");
        List<TbPost> posts = tbPostMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, size));

        List<PostDTO> postDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (TbPost question : posts) {
            TbUser user = tbUserMapper.selectByPrimaryKey(question.getAuthorId());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(question, postDTO);
            postDTO.setUser(user);
            postDTO.setUpdatedStr(DateFormatUtils.format(postDTO.getUpdated(), FORMAT));
            postDTOList.add(postDTO);
        }
        paginationDTO.setData(postDTOList);
        paginationDTO.setTotalCount(totalCount);
        paginationDTO.setPagination(totalPage, page);
        return paginationDTO;
    }

    @Override
    public PostDTO getPostById(Long postId, HttpServletRequest request) {
        //帖子信息
        TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
        List<CommentDTO> collect = commentService.getCommentByPostId(postId).stream().map(e -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(e, commentDTO);
            commentDTO.setCreatedStr(DateFormatUtils.format(e.getCreated(), FORMAT));
            commentDTO.setUser(userInfoService.selectUserByUserId(String.valueOf(e.getAuthorId())));
            return commentDTO;
        }).collect(Collectors.toList());
        //todo
        PostDTO postDTO=new PostDTO();
        BeanUtils.copyProperties(tbPost,postDTO);
        postDTO.setUser(userInfoService.selectUserByUserId(tbPost.getAuthorId().toString()));
        postDTO.setUpdatedStr(DateFormatUtils.format(postDTO.getUpdated(), FORMAT));
        postDTO.setViewCount(tbPost.getViewCount());
        postDTO.setCommentCount(Integer.valueOf(tbPost.getCommentCount().toString()));
        postDTO.setComments(collect);



        //登录用户信息
        TbUser loginUser =  (TbUser)request.getAttribute("loginUser");

        if(loginUser.getId()==tbPost.getAuthorId()){
            postDTO.setCanEdit(true);
            postDTO.setCanDelete(true);
            postDTO.setEssence(true);
        }
        tbPost.setViewCount(tbPost.getViewCount()+VIEW_COUNT_STEP);
        tbPostMapper.updateByPrimaryKey(tbPost);
        return postDTO;
    }
}
