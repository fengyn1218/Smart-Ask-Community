package com.feng.community.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_like")
public class TbLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 点赞目标id
     */
    @Column(name = "target_id")
    private Long targetId;

    /**
     * 目标类型（评论点赞/帖子收藏）
     */
    private Integer type;

    /**
     * 点赞人
     */
    private Long liker;

    /**
     * 点赞时间
     */
    private Date created;

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
     * 获取点赞目标id
     *
     * @return target_id - 点赞目标id
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * 设置点赞目标id
     *
     * @param targetId 点赞目标id
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * 获取目标类型
     *
     * @return type - 目标类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置目标类型
     *
     * @param type 目标类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取点赞人
     *
     * @return liker - 点赞人
     */
    public Long getLiker() {
        return liker;
    }

    /**
     * 设置点赞人
     *
     * @param liker 点赞人
     */
    public void setLiker(Long liker) {
        this.liker = liker;
    }

    /**
     * 获取点赞时间
     *
     * @return created - 点赞时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置点赞时间
     *
     * @param created 点赞时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }
}