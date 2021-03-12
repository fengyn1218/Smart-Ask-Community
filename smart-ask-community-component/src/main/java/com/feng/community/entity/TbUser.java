package com.feng.community.entity;

import java.util.Date;

import javax.persistence.*;

@Table(name = "tb_user")
public class TbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String password;

    /**
     * 是否激活1true2false
     */
    private Integer activating;

    private String email;

    private Date created;

    private Date updated;

    /**
     * 性别 1男2女
     */
    private Long sex;

    /**
     * 头像网址
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 个人签名
     */
    private String signature;

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
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取是否激活1true2false
     *
     * @return activating - 是否激活1true2false
     */
    public Integer getActivating() {
        return activating;
    }

    /**
     * 设置是否激活1true2false
     *
     * @param activating 是否激活1true2false
     */
    public void setActivating(Integer activating) {
        this.activating = activating;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     */
    public void setEmail(String email) {
        this.email = email;
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
     * 获取性别 1男2女
     *
     * @return sex - 性别 1男2女
     */
    public Long getSex() {
        return sex;
    }

    /**
     * 设置性别 1男2女
     *
     * @param sex 性别 1男2女
     */
    public void setSex(Long sex) {
        this.sex = sex;
    }

    /**
     * 获取头像网址
     *
     * @return avatar_url - 头像网址
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 设置头像网址
     *
     * @param avatarUrl 头像网址
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 获取所在城市
     *
     * @return city - 所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置所在城市
     *
     * @param city 所在城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取个人签名
     *
     * @return signature - 个人签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置个人签名
     *
     * @param signature 个人签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }
}