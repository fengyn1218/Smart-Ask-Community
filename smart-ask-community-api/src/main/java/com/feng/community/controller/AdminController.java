package com.feng.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feng.community.admin.service.AdminService;

/**
 * @author fengyunan
 * Created on 2021-03-02
 */
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/index")
    public String index() {
        return adminService.test();
    }
}
