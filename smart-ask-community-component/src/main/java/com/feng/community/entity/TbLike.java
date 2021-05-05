package com.feng.community.entity;

import javax.persistence.*;

@Table(name = "tb_like")
public class TbLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_id")
    private Long targetId;

    /**
     * 目标类型:
     * 1评论点赞
     * 2帖子收藏
     */
    private Integer type;

    private Long liker;

    private Long created;

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
     * @return target_id
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * @param targetId
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * 获取目标类型:
     * 1评论点赞
     * 2帖子收藏
     *
     * @return type - 目标类型:
     * 1评论点赞
     * 2帖子收藏
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置目标类型:
     * 1评论点赞
     * 2帖子收藏
     *
     * @param type 目标类型:
     *             1评论点赞
     *             2帖子收藏
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return liker
     */
    public Long getLiker() {
        return liker;
    }

    /**
     * @param liker
     */
    public void setLiker(Long liker) {
        this.liker = liker;
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
}