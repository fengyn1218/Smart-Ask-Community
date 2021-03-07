package com.feng.community.entity;

import javax.persistence.*;

@Table(name = "tb_notification")
public class TbNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long notifier;

    private Long receiver;

    private Long outerid;

    /**
     * 1回问，2回评，3收藏，4点赞
     */
    private Integer type;

    @Column(name = "gmt_create")
    private Long gmtCreate;

    /**
     * 0未读，1已读
     */
    private Integer status;

    @Column(name = "notifier_name")
    private String notifierName;

    @Column(name = "outer_title")
    private String outerTitle;

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
     * @return notifier
     */
    public Long getNotifier() {
        return notifier;
    }

    /**
     * @param notifier
     */
    public void setNotifier(Long notifier) {
        this.notifier = notifier;
    }

    /**
     * @return receiver
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * @param receiver
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * @return outerid
     */
    public Long getOuterid() {
        return outerid;
    }

    /**
     * @param outerid
     */
    public void setOuterid(Long outerid) {
        this.outerid = outerid;
    }

    /**
     * 获取1回问，2回评，3收藏，4点赞
     *
     * @return type - 1回问，2回评，3收藏，4点赞
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1回问，2回评，3收藏，4点赞
     *
     * @param type 1回问，2回评，3收藏，4点赞
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return gmt_create
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取0未读，1已读
     *
     * @return status - 0未读，1已读
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0未读，1已读
     *
     * @param status 0未读，1已读
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return notifier_name
     */
    public String getNotifierName() {
        return notifierName;
    }

    /**
     * @param notifierName
     */
    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName;
    }

    /**
     * @return outer_title
     */
    public String getOuterTitle() {
        return outerTitle;
    }

    /**
     * @param outerTitle
     */
    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle;
    }
}