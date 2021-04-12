package com.feng.community.controller;

import com.feng.community.annotation.NeedLoginToken;
import com.feng.community.dao.TbPostMapper;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.storage.TagsCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
@Controller
public class PublishController {
    @Autowired
    private TagsCache tagsCache;
    @Autowired
    private TbPostMapper tbPostMapper;

    @NeedLoginToken
    @GetMapping("publish/post")
    public String publish(Model model) {
        model.addAttribute("tags", tagsCache.getTags());
        return "p/add";
    }

    @PostMapping("publish/post")
    public String publish(@RequestParam("title") String title,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam("tag") String tag,
                          @RequestParam("type") Integer type,
                          @RequestParam("permission") Integer permission,
                          HttpServletRequest request,
                          Model model) {
        String defaultDescription = "<p id=\"descriptionP\"></p>";
        description = description.replaceAll("<p id=\"descriptionP\"></p>", ""); //剔出每次编辑产生的冗余p标签
        title = title.trim();
        tag = tag.trim();
        model.addAttribute("title", title);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tagsCache.getTags());
        model.addAttribute("type", type);
      //  model.addAttribute("id", id);
        model.addAttribute("navtype", "publishnav");
        model.addAttribute("permission", permission);
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");

        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "p/add";
        }
        if (defaultDescription.equals(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "p/add";
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "p/add";
        }
        //审核 todo

        TbPost tbPost = new TbPost();
      //  tbPost.setId(id);
        tbPost.setType(Long.valueOf(type));
        tbPost.setCreated(System.currentTimeMillis());
        tbPost.setUpdated(System.currentTimeMillis());
        tbPost.setStatus(1L);
        tbPost.setAuthorId(loginUser.getId());
        tbPost.setTag(tag);
        tbPost.setPermission(permission);
        tbPost.setLikeCount(0);
        tbPost.setViewCount(0);
        tbPost.setCommentCount(0L);
        tbPost.setTitle(title);
        tbPost.setDescription(description);

        tbPostMapper.insert(tbPost);
        return "index";
    }


}
