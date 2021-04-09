package com.feng.community.dto;

import com.feng.community.entity.TbUser;
import lombok.Data;

/**
 * @author: fengyunan
 * Created on: 2021-04-09
 */
@Data
public class CommentDTO {
    private Long id;
    private Long postId;
    private Integer type;
    private Long authorId;
    private Long created;
    private String createdStr; // 评论没必要更新操作
    private Long likeCount = 0L;
    private Integer commentCount = 0;
    private String content;
    private TbUser user;
}
