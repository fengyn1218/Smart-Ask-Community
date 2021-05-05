package com.feng.community.controller;

import com.feng.community.annotation.NeedLoginToken;
import com.feng.community.constant.ResultViewCode;
import com.feng.community.dao.TbPostMapper;
import com.feng.community.dto.PostDTO;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.exception.CustomizeException;
import com.feng.community.service.examine.ExamineService;
import com.feng.community.service.post.PostService;
import com.feng.community.storage.TagsCache;
import com.feng.community.storage.ZeroCommentPostCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private ZeroCommentPostCache zeroCommentPostCache;
    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private PostService postService;
    @Autowired
    private ExamineService examineService;

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
                          @RequestParam(value = "id", required = false) Integer id,
                          HttpServletRequest request,
                          Model model) {
        String defaultDescription = "<p id=\"descriptionP\"></p>";
        description = description.replaceAll("<p id=\"descriptionP\"></p>", ""); //剔出每次编辑产生的冗余p标签
        // 审核用,提取出汉字
        String reg = "[^\u4e00-\u9fa5]";
        String des = description.replaceAll(reg, "");
        title = title.trim();
        tag = tag.trim();
        model.addAttribute("title", title);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tagsCache.getTags());
        model.addAttribute("type", type);
        model.addAttribute("id", id);
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
        //审核
        if (!examineService.isNormal(title) || !examineService.isNormal(des)) {
            model.addAttribute("error", "您输入的内容不符合规定呦！");
            return "p/add";
        }
        TbPost tbPost = new TbPost();
        if (id != null) {
            tbPost.setId(Long.valueOf(id));
        }
        tbPost.setType(Long.valueOf(type));
        tbPost.setUpdated(System.currentTimeMillis());
        tbPost.setStatus(1L);
        tbPost.setAuthorId(loginUser.getId());
        tbPost.setTag(tag);
        tbPost.setPermission(permission);
        tbPost.setTitle(title);
        tbPost.setDescription(description);
        if (id != null) {
            // 更新
            tbPostMapper.updateByPrimaryKey(tbPost);
        } else {
            // 新增
            tbPost.setCreated(System.currentTimeMillis());
            tbPost.setLikeCount(0);
            tbPost.setViewCount(0L);
            tbPost.setCommentCount(0L);

            tbPostMapper.insert(tbPost);
            zeroCommentPostCache.updateZeroCommentPosts(tbPost.getId());
        }
        return "redirect:/index";
    }

    @GetMapping("p/publish/{id}")
    public String edit2(@PathVariable(name = "id") Long id,
                        Model model,
                        HttpServletRequest request) {
        TbUser user = (TbUser) request.getAttribute("loginUser");
        PostDTO postDTO = postService.getById(id, user);
        if (postDTO.getAuthorId().longValue() != user.getId().longValue()) {
            throw new CustomizeException(ResultViewCode.QUESTION_NOT_FOUND);
        }
        model.addAttribute("title", postDTO.getTitle());
        model.addAttribute("description", postDTO.getDescription());
        model.addAttribute("type", postDTO.getType());
        model.addAttribute("tag", postDTO.getTag());
        model.addAttribute("id", postDTO.getId());
        model.addAttribute("tags", tagsCache.getTags());
        model.addAttribute("navtype", "publishnav");
        return "p/add";
    }


}
