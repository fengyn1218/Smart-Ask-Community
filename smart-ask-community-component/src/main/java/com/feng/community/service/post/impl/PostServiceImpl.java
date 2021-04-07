package com.feng.community.service.post.impl;

import com.feng.community.dao.TbPostMapper;
import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.dto.PostDTO;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.service.post.PostService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        example.createCriteria().andEqualTo("type", type);
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
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

        Integer offset = size * (page-1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        example.setOrderByClause("gmt_modified desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            TbUser user = tbUserMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            questionDTO.setUser(userDTO);
            questionDTO.setGmtModifiedStr(timeUtils.getTime(questionDTO.getGmtModified(),null));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setTotalCount(totalCount);
        paginationDTO.setPagination(totalPage,page);
        return paginationDTO;
    }
}
