package com.feng.community.service.user;

import com.feng.community.dto.ResultView;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
public interface UserRegisterService {
    ResultView register(String email, String password, String code);

    ResultView forget(String email, String password, String code);
}
