package com.feng.community;

import static com.feng.community.constant.RedisKey.HOT_TAGS;
import static com.feng.community.constant.RedisKey.SEND_MAIL_CODE;

import com.feng.community.entity.TbUser;
import com.feng.community.storage.LoginUserCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.feng.community.helper.RedisHelper;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

/**
 * @author fengyunan
 * Created on 2021-03-04
 */

@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private LoginUserCache loginUserCache;

    @Test
    public void setLoginUserCache() {
        loginUserCache.putLoginUser(1L, System.currentTimeMillis());
        loginUserCache.putLoginUser(2L, System.currentTimeMillis());
        loginUserCache.putLoginUser(3L, System.currentTimeMillis());
        loginUserCache.putLoginUser(4L, System.currentTimeMillis());
    }

    @Test
    public void set() {
        redisHelper.set("123", "456");
        System.out.println(redisHelper.get("123"));
    }

    @Test
    public void getZset(){
        Set<ZSetOperations.TypedTuple<Object>> zSetRank = redisHelper.getZSetRank(HOT_TAGS.getPrefix(), 0, -1);
        System.out.println(zSetRank);
    }

    @Test
    public void test() {
        redisHelper.clearDB();
    }

    @Test
    public void Test1() {
        loginUserCache.putLoginUser(1L, System.currentTimeMillis());
        List<TbUser> loginUsers = loginUserCache.getLoginUsers();
        loginUsers.stream().map(TbUser::getEmail).forEach(System.out::print);
    }

}
