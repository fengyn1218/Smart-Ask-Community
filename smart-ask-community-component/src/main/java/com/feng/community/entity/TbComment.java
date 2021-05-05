package com.feng.community.entity;

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

    private String content;

    private Long created;

    @Column(name = "like_count")
    private Integer likeCount;

    private Long type;

    @Column(name = "comment_count")
    private Long commentCount;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return author_id
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * @return post_id
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * @param postId
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return created
     */
    public Long getCreated() {
        return created;
    }

    /**
     * @param created
     */
    public void setCreated(Long created) {
        this.created = created;
    }

    /**
     * @return like_count
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * @param likeCount
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * @return type
     */
    public Long getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * @return comment_count
     */
    public Long getCommentCount() {
        return commentCount;
    }

    /**
     * @param commentCount
     */
    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}