package com.feng.community.service.user;

import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;

public interface UserInfoService {
    TbUser selectUserByUserId(String userId);

    int updateAvatarById(Long userId, String url);

    int updateUserInfo(TbUser tbUser);

    ResultView updatePassword(String nowpass, String pass, String id);
}
