package com.feng.community.service.user;

import com.feng.community.entity.TbUser;

/**
 * @author fengyunan
 * Created on 2021-03-05
 */
public interface UserLoginService {
    /**
     * 登录
     */
    TbUser login(String email, String password);
}
