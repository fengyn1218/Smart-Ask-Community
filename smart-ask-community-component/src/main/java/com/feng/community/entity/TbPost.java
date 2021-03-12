package com.feng.community.entity;

import java.util.Date;

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

    /**
     * 悬赏金币
     */
    private Integer gold;

    private Date created;

    private Date updated;

    /**
     * 状态：1:未结。2:已结。3:精华
     */
    private Long status;

    /**
     * 作者id
     */
    @Column(name = "author_id")
    private Long authorId;

    /**
     * 标签
     */
    private String tag;

    private Integer permission;

    /**
     * 收藏数
     */
    @Column(name = "like_count")
    private Integer likeCount;

    /**
     * 点击量
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 标题
     */
    private String title;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *
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
     * 1：提问
     * 2：分享
     * 3：讨论
     * 4：建议
     * 5:公告
     * 6:动态
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * 获取悬赏金币
     *
     * @return gold - 悬赏金币
     */
    public Integer getGold() {
        return gold;
    }

    /**
     * 设置悬赏金币
     *
     * @param gold 悬赏金币
     */
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    /**
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     *
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return updated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     *
     */
    public void setUpdated(Date updated) {
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
     * 获取作者id
     *
     * @return author_id - 作者id
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * 设置作者id
     *
     * @param authorId 作者id
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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
     * @return permission
     */
    public Integer getPermission() {
        return permission;
    }

    /**
     *
     */
    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    /**
     * 获取收藏数
     *
     * @return like_count - 收藏数
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * 设置收藏数
     *
     * @param likeCount 收藏数
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * 获取点击量
     *
     * @return view_count - 点击量
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * 设置点击量
     *
     * @param viewCount 点击量
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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
}