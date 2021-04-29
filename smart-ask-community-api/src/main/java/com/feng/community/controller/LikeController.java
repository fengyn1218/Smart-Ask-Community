package com.feng.community.controller;

import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.service.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fengyunan
 * Created on: 2021-04-24
 */
@RestController
@RequestMapping("like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("post")
    public ResultView likePost(HttpServletRequest request,
                               @RequestParam(name = "postId") Long postId) {
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");
        return likeService.likePost(loginUser, postId);
    }

    @PostMapping("comment")
    public ResultView likeComment(HttpServletRequest request,
                                  @RequestParam(name = "commentId") Long commentId) {
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");
        return likeService.likeComment(loginUser, commentId);
    }

    @PostMapping("cancel")
    public ResultView cancel(HttpServletRequest request,
                             @RequestParam(name = "postId") Long postId) {
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");
        return likeService.cancel(loginUser, postId);
    }

}
