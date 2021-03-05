package com.feng.community;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.entity.TbUser;

/**
 * @author fengyunan
 * Created on 2021-03-05
 */
@SpringBootTest
public class TestDataBase {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Test
    public void select() {
        TbUser user = new TbUser();
        user.setEmail("123");
        user.setPassword("123");
        user.setSex(1L);
        user.setActivating(1);
        user.setSignature("123");
        int insert = tbUserMapper.insert(user);
        System.out.println(insert);
    }
}
