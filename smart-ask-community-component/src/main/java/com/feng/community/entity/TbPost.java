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
1：提问
2：分享
3：讨论
4：建议
5:公告
6:动态
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
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取所属专栏 ：
1：提问
2：分享
3：讨论
4：建议
5:公告
6:动态
     *
     * @return type - 所属专栏 ：
1：提问
2：分享
3：讨论
4：建议
5:公告
6:动态
     */
    public Long getType() {
        return type;
    }

    /**
     * 设置所属专栏 ：
1：提问
2：分享
3：讨论
4：建议
5:公告
6:动态
     *
     * @param type 所属专栏 ：
1：提问
2：分享
3：讨论
4：建议
5:公告
6:动态
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
     * @param created
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
     * @param updated
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