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
    private Integer likeCount = 0;
    private Long commentCount = 0L;
    private String content;
    private TbUser user;
}
