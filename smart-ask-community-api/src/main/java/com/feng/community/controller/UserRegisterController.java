package com.feng.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feng.community.dto.ResultView;
import com.feng.community.service.user.UserRegisterService;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Controller
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @ResponseBody
    @PostMapping("register")
    public ResultView register(@RequestParam(name = "mail", required = true) String mail,
            @RequestParam(name = "password", required = true) String password,
            @RequestParam(name = "code", required = true) String code
    ) {
        return userRegisterService.register(mail, password, code);
    }
}
