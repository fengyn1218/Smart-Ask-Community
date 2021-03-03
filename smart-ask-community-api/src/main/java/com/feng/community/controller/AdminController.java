package com.feng.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.feng.community.admin.service.AdminService;

/**
 * @author fengyunan
 * Created on 2021-03-02
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
