package com.feng.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author fengyunan
 * Created on 2021-03-04
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index1";
    }

}
