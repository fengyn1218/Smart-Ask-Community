package com.feng.community.controller;

import com.feng.community.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
@Controller
public class PostController {

    @Autowired
    private PostService postService;
}
