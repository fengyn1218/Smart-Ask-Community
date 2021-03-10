package com.feng.community.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.feng.community.entity.TbUser;
import com.feng.community.service.user.UserLoginService;
import com.feng.community.utils.CookieUtils;
import com.feng.community.utils.TokenUtils;

/**
 * @author fengyunan
 * Created on 2021-03-05
 */
@Controller
public class UserLoginController {
    // Cookie过期时间
    private static final int COOKIE_TIME = 86400 * 3;

    @Autowired
    private UserLoginService userLoginService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam(required = true) String email, @RequestParam(required = true) String password,
            Model model, HttpServletRequest request, HttpServletResponse response) {
        TbUser tbUser = userLoginService.login(email, password);
        //登录失败
        if (null == tbUser) {
            model.addAttribute("msg", "用户名或密码错误，请重新输入");
            return "login";
        }
        //登陆成功
        else {
            //将登陆信息放入会话
            request.getSession().setAttribute("user", tbUser);
            CookieUtils.setCookie(request, response, "token", TokenUtils.getToken(tbUser), COOKIE_TIME);
            model.addAttribute("user", tbUser);
            //重定向，默认是转发
            return "redirect:/index";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        // 从会话中删除
        // request.getSession().removeAttribute("user");
        //todo
        return null;
    }

}
