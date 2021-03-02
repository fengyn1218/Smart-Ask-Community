package com.feng.community.admin.service.impl;

import org.springframework.stereotype.Service;

import com.feng.community.admin.service.AdminService;

/**
 * @author fengyunan <fengyunan@kuaishou.com>
 * Created on 2021-03-02
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public String test() {
        return "测试";
    }
}
