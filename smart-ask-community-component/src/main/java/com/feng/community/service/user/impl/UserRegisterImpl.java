package com.feng.community.service.user.impl;

import static com.feng.community.constant.RedisKey.SEND_MAIL_CODE;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.helper.MailHelper;
import com.feng.community.helper.RedisHelper;
import com.feng.community.service.user.UserRegisterService;

import tk.mybatis.mapper.entity.Example;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Service
public class UserRegisterImpl implements UserRegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private RedisHelper redisHelper;

    @Override
    public ResultView register(String email, String password, String code) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("email", email);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        // 邮箱重复
        if (!CollectionUtils.isEmpty(tbUsers)) {
            return ResultView.fail("注册失败，该邮箱已经被使用了，换一个试试。");
        } else {
            String cacheCode = (String) redisHelper.get(SEND_MAIL_CODE.of(email));
            // 对比缓存
            if (StringUtils.isEmpty(cacheCode)) {
                return ResultView.fail("验证码已失效，请重新获取！");
            } else if (!code.equals(cacheCode)) {
                return ResultView.fail("验证码不正确，请输入正确的验证码！");
            } else if (!StringUtils.isEmpty(cacheCode)) {
                TbUser tbUser = new TbUser();
                tbUser.setEmail(email);
                // 明文密码加密
                tbUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
                tbUser.setCreated(new Date());
                return ResultView.success("注册成功，去登录吧！");
            }
        }
        return ResultView.fail("某些未知的错误，哈哈哈！你注册失败了！");
    }
}
