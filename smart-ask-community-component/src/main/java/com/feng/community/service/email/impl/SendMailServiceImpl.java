package com.feng.community.service.email.impl;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.entity.TbUser;
import com.feng.community.helper.RedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.community.dto.ResultView;
import com.feng.community.helper.MailHelper;
import com.feng.community.service.email.SendMailService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

import static com.feng.community.constant.RedisKey.SEND_MAIL_CODE;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private TbUserMapper tbUserMapper;


    @Override
    public ResultView sendMail(String email) {
        return mailHelper.sendMail(email);
    }

    @Override
    public ResultView updateEmail(String email, String code, String id) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("email", email);
        // 从数据库拿到信息
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
                TbUser tbUser = tbUserMapper.selectByPrimaryKey(id);
                tbUser.setEmail(email);
                tbUserMapper.updateByPrimaryKey(tbUser);
                return ResultView.success("修改信息成功，去登录吧！");
            }
        }
        return ResultView.fail("某些未知的错误，兜个底！你修改失败了！");
    }
}
