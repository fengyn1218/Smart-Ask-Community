package com.feng.community.entity;

import javax.persistence.*;

@Table(name = "tb_post")
public class TbPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属专栏 ：
     * 1：提问
     * 2：分享
     * 3：讨论
     * 4：建议
     * 5:公告
     * 6:动态
     */
    private Long type;

    private Long created;

    private Long updated;

    /**
     * 状态：1:未结。2:已结。3:精华
     */
    private Long status;

    @Column(name = "author_id")
    private Long authorId;

    private String tag;

    private Integer permission;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "gmt_latest_comment")
    private Long gmtLatestComment;

    @Column(name = "comment_count")
    private Long commentCount;

    private String title;

    private String description;

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
     * 获取所属专栏 ：
     * 1：提问
     * 2：分享
     * 3：讨论
     * 4：建议
     * 5:公告
     * 6:动态
     *
     * @return type - 所属专栏 ：
     * 1：提问
     * 2：分享
     * 3：讨论
     * 4：建议
     * 5:公告
     * 6:动态
     */
    public Long getType() {
        return type;
    }

    /**
     * 设置所属专栏 ：
     * 1：提问
     * 2：分享
     * 3：讨论
     * 4：建议
     * 5:公告
     * 6:动态
     *
     * @param type 所属专栏 ：
     *             1：提问
     *             2：分享
     *             3：讨论
     *             4：建议
     *             5:公告
     *             6:动态
     */
    public void setType(Long type) {
        this.type = type;
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
     * @return updated
     */
    public Long getUpdated() {
        return updated;
    }

    /**
     * @param updated
     */
    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    /**
     * 获取状态：1:未结。2:已结。3:精华
     *
     * @return status - 状态：1:未结。2:已结。3:精华
     */
    public Long getStatus() {
        return status;
    }

    /**
     * 设置状态：1:未结。2:已结。3:精华
     *
     * @param status 状态：1:未结。2:已结。3:精华
     */
    public void setStatus(Long status) {
        this.status = status;
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
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return permission
     */
    public Integer getPermission() {
        return permission;
    }

    /**
     * @param permission
     */
    public void setPermission(Integer permission) {
        this.permission = permission;
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
     * @return view_count
     */
    public Long getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount
     */
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return gmt_latest_comment
     */
    public Long getGmtLatestComment() {
        return gmtLatestComment;
    }

    /**
     * @param gmtLatestComment
     */
    public void setGmtLatestComment(Long gmtLatestComment) {
        this.gmtLatestComment = gmtLatestComment;
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

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}