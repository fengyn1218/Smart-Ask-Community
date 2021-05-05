package com.feng.community.entity;

import javax.persistence.*;

@Table(name = "talk")
public class Talk {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 标签
     */
    private String tag;

    /**
     * 图片地址
     */
    private String images;

    /**
     * 视频地址
     */
    private String video;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 权限
     */
    private Integer permission;

    /**
     * 发布者
     */
    private Long creator;

    /**
     * 浏览数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 评论数
     */
    @Column(name = "comment_count")
    private Integer commentCount;

    /**
     * 点赞数
     */
    @Column(name = "like_count")
    private Integer likeCount;

    /**
     * 最后评论时间
     */
    @Column(name = "gmt_latest_comment")
    private Long gmtLatestComment;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 修改时间
     */
    private Long updated;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取标签
     *
     * @return tag - 标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
     *
     * @param tag 标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取图片地址
     *
     * @return images - 图片地址
     */
    public String getImages() {
        return images;
    }

    /**
     * 设置图片地址
     *
     * @param images 图片地址
     */
    public void setImages(String images) {
        this.images = images;
    }

    /**
     * 获取视频地址
     *
     * @return video - 视频地址
     */
    public String getVideo() {
        return video;
    }

    /**
     * 设置视频地址
     *
     * @param video 视频地址
     */
    public void setVideo(String video) {
        this.video = video;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取权限
     *
     * @return permission - 权限
     */
    public Integer getPermission() {
        return permission;
    }

    /**
     * 设置权限
     *
     * @param permission 权限
     */
    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    /**
     * 获取发布者
     *
     * @return creator - 发布者
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * 设置发布者
     *
     * @param creator 发布者
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * 获取浏览数
     *
     * @return view_count - 浏览数
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * 设置浏览数
     *
     * @param viewCount 浏览数
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * 获取评论数
     *
     * @return comment_count - 评论数
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * 设置评论数
     *
     * @param commentCount 评论数
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * 获取点赞数
     *
     * @return like_count - 点赞数
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * 设置点赞数
     *
     * @param likeCount 点赞数
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * 获取最后评论时间
     *
     * @return gmt_latest_comment - 最后评论时间
     */
    public Long getGmtLatestComment() {
        return gmtLatestComment;
    }

    /**
     * 设置最后评论时间
     *
     * @param gmtLatestComment 最后评论时间
     */
    public void setGmtLatestComment(Long gmtLatestComment) {
        this.gmtLatestComment = gmtLatestComment;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Long getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Long created) {
        this.created = created;
    }

    /**
     * 获取修改时间
     *
     * @return updated - 修改时间
     */
    public Long getUpdated() {
        return updated;
    }

    /**
     * 设置修改时间
     *
     * @param updated 修改时间
     */
    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}