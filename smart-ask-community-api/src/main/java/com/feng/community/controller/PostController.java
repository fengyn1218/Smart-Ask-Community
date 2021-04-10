package com.feng.community.controller;

import com.feng.community.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
@Controller
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("{postId}")
    public String getPostInfo(@PathVariable Long postId, Model model) {
        model.addAttribute("post", postService.getPostById(postId));
        return "/p/detail";
    }
}
