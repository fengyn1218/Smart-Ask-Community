package com.feng.community.storage;

import com.feng.community.dao.TbUserMapper;
import com.feng.community.entity.TbUser;
import com.feng.community.helper.RedisHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.feng.community.constant.RedisKey.LOGIN_USERS;

/**
 * 暂时用作网站最近访客
 *
 * @author: fengyunan
 * Created on: 2021-04-06
 */

@Data
@Component
public class LoginUserCache {

    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private TbUserMapper tbUserMapper;

    private List<TbUser> loginUsers = new ArrayList<>();
    public static final long LOGIN_USER_EXPIRE_TIME = 10 * 60; // 缓存过期时间
    public static final int ZSET_MAX_SIZE = 15;

    public void updateLoginUsers(List<TbUser> loginUsers) {
        this.loginUsers = loginUsers;
    }


    public void putLoginUser(Long uid, Long gmt) {
        redisHelper.addZSetValue(LOGIN_USERS.getPrefix(), uid, gmt);
        // 裁剪15个
        checkLoginUser();
    }

    public Map<Object, Object> getLoginUserMap() {
        return redisHelper.getZSetRank(LOGIN_USERS.getPrefix(), 0, -1)
                .stream()
                .collect(
                        Collectors.toMap(ZSetOperations.TypedTuple::getValue,
                                ZSetOperations.TypedTuple::getScore,
                                (o, n) -> n)
                );
    }

    public List<TbUser> getLoginUsers() {
        return getLoginUserMap().keySet().stream()
                .map(
                        e -> {
                            Long uid = Long.valueOf(String.valueOf(e));
                            Example example = new Example(TbUser.class);
                            example.createCriteria().andEqualTo("id", uid);
                            List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
                            return tbUsers.get(0);
                        }
                ).collect(Collectors.toList());
    }

    public void checkLoginUser() {
        String key = LOGIN_USERS.getPrefix();
        long zSetSize = redisHelper.getZSetSize(key);
        // 之保留缓存内15个最近登录用户
        if (zSetSize > ZSET_MAX_SIZE) {
            redisHelper.removeZsetByRange(key, 0, zSetSize - ZSET_MAX_SIZE);
        }
    }
}
