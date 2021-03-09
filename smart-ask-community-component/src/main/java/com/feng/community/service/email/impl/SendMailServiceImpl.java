package com.feng.community.service.email.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.community.dto.ResultView;
import com.feng.community.helper.MailHelper;
import com.feng.community.service.email.SendMailService;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private MailHelper mailHelper;

    @Override
    public ResultView sendMail(String email) {
        return mailHelper.sendMail(email);
    }
}
