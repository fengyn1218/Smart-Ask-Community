package com.feng.community.dto;

import lombok.Data;

/**
 * @author: fengyunan
 * Created on: 2021-04-09
 */
@Data
public class CommentQueryDTO {
    private Long id;
    private Long postId;
    private Integer type;
    private Long authorId;
    private Integer page;
    private Integer size;
    private Integer offset;
    private String sort;
    private String order;

    // 转化非法参数
    public void convert() {
        if (this.page == null || this.page <= 0) this.page = 1;
        if (this.size == null || this.size <= 0 || this.size > 20) this.size = 5;
        if (!("created".equals(this.sort)))
            this.sort = "created";
        if (!("desc".equals(this.order) || "asc".equals(this.order))) this.order = "desc";
    }
}
