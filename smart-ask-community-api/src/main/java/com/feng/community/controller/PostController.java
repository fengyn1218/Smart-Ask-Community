package com.feng.community.controller;

import com.feng.community.annotation.NeedLoginToken;
import com.feng.community.constant.ResultViewCode;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.entity.TbUser;
import com.feng.community.exception.CustomizeException;
import com.feng.community.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
@Controller
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @NeedLoginToken
    @GetMapping("{postId}")
    public String getPostInfo(@PathVariable Long postId, Model model, HttpServletRequest request) {
        // todo 处理尽自己可见问题
        model.addAttribute("postDTO", postService.getPostById(postId, request));
        model.addAttribute("relatedQuestions", postService.getRelatedPosts(postId));
        return "/p/detail";
    }

    @NeedLoginToken
    @GetMapping("/user/p/{action}")
    public String p(HttpServletRequest request,
                    @PathVariable(name = "action") String action,
                    Model model,
                    @RequestParam(name = "page", defaultValue = "1") Integer page,
                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        TbUser user = (TbUser) request.getAttribute("loginUser");

        if ("myPosts".equals(action)) {
            model.addAttribute("section", "myPosts");
            model.addAttribute("sectionName", "我的帖子");
            PaginationDTO pagination = postService.listByUserId(user.getId(), page, size);
            model.addAttribute("pagination", pagination);
            model.addAttribute("navtype", "communitynav");
        }
        if ("likes".equals(action)) {
            PaginationDTO paginationDTO = postService.listByExample(user.getId(), page, size, "likes");
            model.addAttribute("section", "likes");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("sectionName", "我的收藏");
            model.addAttribute("navtype", "communitynav");
        }
        return "user/p";
    }

    @PostMapping("/del/id")
    @ResponseBody
    public Map<String, Object> delQuestionById(HttpServletRequest request,
                                               @RequestParam(name = "id", defaultValue = "0") Long id) {
        TbUser user = (TbUser) request.getAttribute("loginUser");
        if (user == null) {
            throw new CustomizeException(ResultViewCode.NEED_LOGIN);
        }
        Map<String, Object> map = new HashMap<>();
        if (id == null || id == 0) {
            map.put("code", 500);
            map.put("msg", "妈呀，出问题啦！");
        } else {
            int c = postService.delPostById(user.getId(), id);
            if (c == 0) {
                map.put("code", 500);
                map.put("msg", "哎呀，该贴已删除或您无权删除！");
            } else {
                map.put("code", 200);
                map.put("msg", "恭喜您，成功删除" + c + "条帖子！");
            }
        }
        return map;
    }

}
