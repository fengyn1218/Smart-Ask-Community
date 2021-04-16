package com.feng.community.controller;

import com.feng.community.annotation.NeedLoginToken;
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

    @NeedLoginToken
    @PostMapping("updateEmail")
    public ResultView updateEmail(
            @RequestParam("mail") String mail,
            @RequestParam("code") String code,
            @RequestParam("id") String id
    ) {
        return sendMailService.updateEmail(mail, code, id);
    }
}



