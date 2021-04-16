package com.feng.community.service.user.impl;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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

    @Override
    public int updateUserInfo(TbUser tbUser) {
        TbUser dbUser = selectUserByUserId(String.valueOf(tbUser.getId()));
        dbUser.setUserName(tbUser.getUserName());
        dbUser.setCity(tbUser.getCity());
        dbUser.setSex(tbUser.getSex());
        dbUser.setUpdated(tbUser.getUpdated());
        dbUser.setSignature(tbUser.getSignature());
        return tbUserMapper.updateByPrimaryKey(dbUser);
    }

    @Override
    public ResultView updatePassword(String nowpass, String pass, String id) {
        TbUser dbUser = selectUserByUserId(id);
        // 明文密码加密
        String now = DigestUtils.md5DigestAsHex(nowpass.getBytes());
        if (!dbUser.getPassword().equals(now)) {
            return ResultView.fail("你输入的原密码不正确哦！");
        } else {
            dbUser.setPassword(DigestUtils.md5DigestAsHex(pass.getBytes()));
            tbUserMapper.updateByPrimaryKey(dbUser);
            return ResultView.success("修改成功！");
        }
    }
}
