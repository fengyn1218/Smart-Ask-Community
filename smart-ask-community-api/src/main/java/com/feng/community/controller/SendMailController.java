package com.feng.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.feng.community.dto.ResultView;
import com.feng.community.service.email.SendMailService;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@RestController
@RequestMapping(value = "mail")
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("send")
    public ResultView sendMail(@RequestParam("mail") String email) {
        return sendMailService.sendMail(email);
    }
}



