package com.feng.community.service.post;

import com.feng.community.dto.PaginationDTO;
import com.feng.community.dto.PostDTO;
import com.feng.community.entity.TbPost;

import java.util.List;

public interface PostService {
    /**
     * @description: 获取观看数量Top的帖子
     * @param: search
     * @param: tag
     * @param: sort
     * @param: type
     * @return: java.util.List<com.feng.community.entity.TbPost>
     * @author: fengyunan
     * @date: 2021-04-0516:29
     * @version:1.0.0
     **/
    List<TbPost> getTopPost(String search, String tag, String sort, Integer type);

    // 根据类型获取帖子
    PaginationDTO<TbPost> getPostByType(String search, String tag, String sort, Integer page, Integer size, Integer type);

    PaginationDTO<TbPost> listByUserId(Long userId, Integer page, Integer size);

    PostDTO getPostById(Long postId);
}
