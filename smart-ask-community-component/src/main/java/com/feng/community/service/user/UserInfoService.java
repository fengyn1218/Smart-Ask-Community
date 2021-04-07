package com.feng.community.service.user;

import com.feng.community.entity.TbUser;

public interface UserInfoService {
    TbUser selectUserByUserId(String userId);
}
