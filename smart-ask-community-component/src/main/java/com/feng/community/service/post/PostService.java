package com.feng.community.service.post;

import com.feng.community.dto.PaginationDTO;
import com.feng.community.dto.PostDTO;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fengyunan
 * Created on 2021-04-15
 */
public interface PostService {

    // 首页置顶top帖子
    List<PostDTO> getTopPost(String search, String tag, String sort, Integer type);

    // 根据类型获取帖子
    PaginationDTO<PostDTO> getPostByType(String search, String tag, String sort, Integer page, Integer size, Integer type);

    PaginationDTO<TbPost> listByUserId(Long userId, Integer page, Integer size);

    PostDTO getPostById(Long postId, HttpServletRequest request);

    // 个人信息栏帖子信息
    PaginationDTO listByExample(Long userId, Integer page, Integer size, String likes);

    PostDTO getById(Long id, TbUser user);

    int delPostById(Long userId, Long postId);

    // 获取作者相关帖子，后续可用redis取人气最高的
    List<PostDTO> getRelatedPosts(Long postId);

    // 根据id获取帖子所属分类
    long getPostTypeById(long postId);
}
