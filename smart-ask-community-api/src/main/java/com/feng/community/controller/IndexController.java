package com.feng.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fengyunan
 * Created on 2021-03-04
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "15") Integer size,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "search", required = false) String search
    ) {

        return "index1";
    }

}
