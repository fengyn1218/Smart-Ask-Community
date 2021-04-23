package com.feng.community.controller;

import com.feng.community.annotation.NeedLoginToken;
import com.feng.community.constant.ResultViewCode;
import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.exception.CustomizeException;
import com.feng.community.service.post.PostService;
import com.feng.community.service.user.UserInfoService;
import com.feng.community.utils.CookieUtils;
import com.feng.community.utils.MapperUtils;
import com.feng.community.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    private TbUserMapper tbUserMapper;

    @Value("***")
    private String vaptcha_vid;
    @Value("0")
    private Integer smsEnable;
    private static final String UPLOAD_PATH = "/smart-ask-community-component/src/main/resources/static/images";
    private static final String FILE_PATH = "/static/images/";

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

    @PostMapping("/user/set/{action}")
    @ResponseBody
    public Map<String, Object> set(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @PathVariable(name = "action") String action
            , @RequestParam(name = "avatar", defaultValue = "127.0.0.1:8080/images/default-avatar.png") String avatar
            , @RequestParam(name = "json", required = false) String json
            , @RequestParam(name = "data", required = false) String data) {

        TbUser user = (TbUser) request.getAttribute("loginUser");
        if (user == null) {
            throw new CustomizeException(ResultViewCode.NEED_LOGIN);
        }
        Map<String, Object> map = new HashMap<>();
        // 修改头像
        if ("avatar".equals(action)) {
            int i = userInfoService.updateAvatarById(user.getId(), avatar);
            if (i == 1) {
                map.put("code", 200);
                map.put("msg", "恭喜您，头像修改成功！！！");
            } else {
                map.put("code", 500);
                map.put("msg", "妈呀，出问题啦！");
            }
        }
        try {
            Map<String, Object> stringObjectMap = MapperUtils.json2map(json);

            // 修改其他信息
            TbUser tbUser = new TbUser();
            String city = stringObjectMap.get("P1") + "-" + stringObjectMap.get("C1") + "-" + stringObjectMap.get("A1");
            long s = 1;
            Object sex = stringObjectMap.get("sex");
            if ("男".equals(sex)) {
                s = 1;
            } else if ("女".equals(sex)) {
                s = 2;
            }
            tbUser.setId(user.getId());
            tbUser.setUserName((String) stringObjectMap.get("username"));
            tbUser.setUpdated(System.currentTimeMillis());
            tbUser.setSex(s);
            tbUser.setCity(city);
            tbUser.setSignature((String) stringObjectMap.get("signature"));

            int i = userInfoService.updateUserInfo(tbUser);

            if (i != 1) {
                map.put("code", 500);
                map.put("msg", "妈呀，资料修改失败啦！");
            } else {
                TbUser byUserId = userInfoService.selectUserByUserId(String.valueOf(user.getId()));
                // 重新设置Cookie
                CookieUtils.setCookie(request, response, "token", TokenUtils.getToken(byUserId), 86400);
                map.put("code", 200);
                map.put("msg", "恭喜您，资料修改成功！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @NeedLoginToken
    @GetMapping("/user/set/{action}")
    public String getSetPage(HttpServletRequest request,
                             @PathVariable(name = "action") String action,
                             Model model) {
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");
        if (loginUser == null) {
            throw new CustomizeException(ResultViewCode.NEED_LOGIN);
        }
        TbUser user = tbUserMapper.selectByPrimaryKey(loginUser.getId());

        if ("info".equals(action) || StringUtils.isBlank(action)) {
            model.addAttribute("user", user);
            model.addAttribute("section", "info");
            model.addAttribute("sectionName", "我的资料");
            model.addAttribute("sectionInfo", "绑定邮箱账号后，您可以使用邮箱账号登录本站，也可用邮箱账号找回密码\n");
            model.addAttribute("navtype", "communitynav");
            return "user/set";
        }
        if ("account".equals(action)) {
            model.addAttribute("section", "account");
            model.addAttribute("sectionName", "绑定/更新邮箱账号");
            model.addAttribute("sectionInfo", "绑定邮箱账号后，您可以使用邮箱账号登录本站，也可用邮箱账号找回密码\n");
            model.addAttribute("navtype", "communitynav");
            model.addAttribute("user", user);
            return "user/account";
        }
        return "user/set";
    }

    //头像上传接口
    @NeedLoginToken
    @PostMapping("/file/avatar")
    @ResponseBody
    public Map<String, Object> uploadAvatar(HttpServletResponse response,
                                            HttpServletRequest request,
                                            @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {

            TbUser user = (TbUser) request.getAttribute("loginUser");
            //获取文件名后缀
            String fileName = file.getOriginalFilename();
            String fileSussix = fileName.substring(fileName.lastIndexOf("."));
            //上传图片文件存放路径
            String realPath = System.getProperty("user.dir") + UPLOAD_PATH;
            String fileAfterName = UUID.randomUUID() + fileSussix;

            File file1 = new File(realPath, fileAfterName);
            if (!file1.exists()) {
                file1.mkdir();
            }
            //将文件写入目标
            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            map.put("status", 0);
            map.put("msg", "");
            map.put("url", FILE_PATH + fileAfterName);
            user.setAvatarUrl(FILE_PATH + fileAfterName);
            // 重新设置Cookie
            CookieUtils.setCookie(request, response, "token", TokenUtils.getToken(user), 86400);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 500);
            map.put("msg", "上传失败");
            map.put("url", null);
            return map;
        }
    }

    //图片上传接口
    @NeedLoginToken
    @PostMapping("/file/layUpload")
    @ResponseBody
    public Map<String, Object> uploadLayImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            TbUser user = (TbUser) request.getAttribute("loginUser");
            //获取文件名后缀
            String fileName = file.getOriginalFilename();
            String fileSussix = fileName.substring(fileName.lastIndexOf("."));
            //上传图片文件存放路径

            String realPath = System.getProperty("user.dir") + UPLOAD_PATH;
            String fileAfterName = UUID.randomUUID() + fileSussix;

            File file1 = new File(realPath, fileAfterName);
            if (!file1.exists()) {
                file1.mkdir();
            }
            //将文件写入目标
            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("code", 0);
            map.put("msg", "");
            map.put("data", FILE_PATH + fileAfterName);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "上传失败");
            map.put("data", null);
            return map;
        }
    }

    // 修改密码
    @NeedLoginToken
    @ResponseBody
    @PostMapping("/user/updatePass")
    public ResultView updatePass(@RequestParam("nowpass") String nowpass,
                                 @RequestParam("pass") String pass,
                                 @RequestParam("id") String id) {
        return userInfoService.updatePassword(nowpass, pass, id);
    }

}
