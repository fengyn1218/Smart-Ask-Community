package com.feng.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.feng.community.dto.ResultView;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Controller
public class UserRegisterController {


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping
    public ResultView register(@RequestParam(name = "register", required = true) String email,
            @RequestParam(name = "password", required = true) String password,
            @RequestParam(name = "code", required = true) String code
    ) {
        //todo
        return null;
    }
}
