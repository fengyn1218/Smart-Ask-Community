package com.feng.community.controller;

import com.feng.community.constant.ResultViewCode;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.entity.TbUser;
import com.feng.community.service.post.PostService;
import com.feng.community.service.user.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fengyunan
 * Created on: 2021-04-07
 */
@Controller
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PostService postService;

    @GetMapping("/user/{userId}")
    public String getUserInfo(@PathVariable String userId,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "12") Integer size,
                              Model model,
                              HttpServletRequest request) {
        if (userId == null || StringUtils.isBlank(userId)) {
            // todo  日志
            return "redirect:/index";
        }
        TbUser user = userInfoService.selectUserByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            PaginationDTO paginationDTO = postService.listByUserId(user.getId(), page, size);
            model.addAttribute("paginationDto", paginationDTO);
            return "home";
        } else {
            model.addAttribute("errorCode", ResultViewCode.USER_IS_EMPTY.getCode());
            model.addAttribute("message", ResultViewCode.USER_IS_EMPTY.getMsg());
            return "error";
        }
    }
}