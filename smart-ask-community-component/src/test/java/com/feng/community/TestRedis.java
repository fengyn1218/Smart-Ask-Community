package com.feng.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.feng.community.helper.RedisHelper;

/**
 * @author fengyunan <fengyunan@kuaishou.com>
 * Created on 2021-03-04
 */

@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisHelper redisHelper;

    @Test
    public void set() {
        redisHelper.set("123","456");
        System.out.println(redisHelper.get("123"));
    }


}
