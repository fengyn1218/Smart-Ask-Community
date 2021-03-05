package com.feng.community.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.entity.TbUser;
import com.feng.community.service.user.UserLoginService;

import tk.mybatis.mapper.entity.Example;

/**
 * @author fengyunan
 * Created on 2021-03-05
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser login(String email, String password) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("email", email);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(tbUsers)) {
            TbUser user = tbUsers.get(0);
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
