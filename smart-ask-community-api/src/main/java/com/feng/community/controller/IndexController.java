package com.feng.community.controller;

import javax.servlet.http.HttpServletRequest;

import com.feng.community.dto.PaginationDTO;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.service.post.PostService;
import com.feng.community.storage.LoginUserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fengyunan
 * Created on 2021-03-04
 */
@Controller
public class IndexController {

    4

    @Autowired
    private LoginUserCache loginUserCache;
    @Autowired
    private PostService postService;

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "15") Integer size,
                        @RequestParam(name = "type", required = false) Integer type,
                        @RequestParam(name = "sort", required = false) String sort,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "search", required = false) String search
    ) {
        List<TbPost> topPosts = postService.getTopPost(search, tag, sort, type);

        PaginationDTO<TbPost> pagination = postService.getPostByType(search, tag, sort, page, size, type);

        List<TbUser> loginUsers = loginUserCache.getLoginUsers();

        model.addAttribute("loginUsers", loginUsers);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        //  model.addAttribute("tags", tags);
        model.addAttribute("sort", sort);
        model.addAttribute("column", type);
        model.addAttribute("topQuestions", topPosts);
        model.addAttribute("navtype", "communitynav");
        return "index";
    }

}
