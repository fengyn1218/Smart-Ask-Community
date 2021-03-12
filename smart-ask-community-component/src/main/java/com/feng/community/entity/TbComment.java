package com.feng.community.entity;

import java.util.Date;

import javax.persistence.*;

@Table(name = "tb_comment")
public class TbComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "post_id")
    private Long postId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 点赞数
     */
    @Column(name = "like_count")
    private Integer likeCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return author_id
     */
    public Long getAuthorId() {
        return authorId;
    }


    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * @return post_id
     */
    public Long getPostId() {
        return postId;
    }


    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
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
}