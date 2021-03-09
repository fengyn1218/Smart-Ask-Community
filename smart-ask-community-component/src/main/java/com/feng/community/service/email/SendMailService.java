package com.feng.community.service.email;

import com.feng.community.dto.ResultView;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
public interface SendMailService {
    ResultView sendMail(String email);
}
