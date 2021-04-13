package com.feng.community.service.user.impl;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.entity.TbUser;
import com.feng.community.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: fengyunan
 * Created on: 2021-04-07
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selectUserByUserId(String userId) {
        Long id = Long.parseLong(userId);
        return tbUserMapper.selectByPrimaryKey(id);

    }

    @Override
    public int updateAvatarById(Long userId, String url) {
        TbUser user = tbUserMapper.selectByPrimaryKey(userId);
        user.setAvatarUrl(url);
        return tbUserMapper.updateByPrimaryKey(user);
    }
}
