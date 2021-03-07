package com.feng.community.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.service.user.UserRegisterService;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Service
public class UserRegisterImpl implements UserRegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public ResultView register(String email, String password, String code) {
        TbUser user = new TbUser();
        //todo
        return ResultView.success("注册成功，去登录吧！");
    }
}
