package com.feng.community.storage;


import com.feng.community.dto.TagsDTO;
import com.feng.community.helper.RedisHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.feng.community.constant.RedisKey.*;

/**
 * @author: fengyunan
 * Created on: 2021-04-12
 */
@Data
@Component
public class TagsCache {
    @Autowired
    private RedisHelper redisHelper;

    @PostConstruct
    private void init() {
        if (!redisHelper.hasKey(TAGS_COMMON.getPrefix())) {
            redisHelper.sSet(TAGS_COMMON.getPrefix(), "笔记", "讨论", "闲聊", "教程", "公告", "灌水", "表白", "分享");
        }
        if (!redisHelper.hasKey(TAGS_PROGRAM.getPrefix())) {
            redisHelper.sSet(TAGS_PROGRAM.getPrefix(), "python", "java", "c++", "c", "html5", "javascript", "php", "css", "html", "node.js", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl");
        }
        if (!redisHelper.hasKey(TAGS_FRAMEWORK.getPrefix())) {
            redisHelper.sSet(TAGS_FRAMEWORK.getPrefix(), "spring", "springboot", "mybatis", "springmvc", "laravel", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts");
        }
        if (!redisHelper.hasKey(TAGS_TOOL.getPrefix())) {
            redisHelper.sSet(TAGS_TOOL.getPrefix(), "tomcat", "linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "负载均衡", "unix", "hadoop", "windows-server");
        }
        if (!redisHelper.hasKey(TAGS_DB.getPrefix())) {
            redisHelper.sSet(TAGS_DB.getPrefix(), "mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite");
        }
        if (!redisHelper.hasKey(TAGS_OTHER.getPrefix())) {
            redisHelper.sSet(TAGS_OTHER.getPrefix(), "分享", "套路", "数据结构", "搬砖");
        }
    }

    public List<TagsDTO> getTags() {
        List<TagsDTO> result = new ArrayList<>();

        TagsDTO common = new TagsDTO();
        common.setCategoryName("常用");
        common.setTags(new ArrayList(redisHelper.sGet(TAGS_COMMON.getPrefix())));
        result.add(common);

        TagsDTO program = new TagsDTO();
        program.setCategoryName("编程语言");
        program.setTags(new ArrayList(redisHelper.sGet(TAGS_PROGRAM.getPrefix())));
        result.add(program);

        TagsDTO framework = new TagsDTO();
        framework.setCategoryName("框架");
        framework.setTags(new ArrayList(redisHelper.sGet(TAGS_FRAMEWORK.getPrefix())));
        result.add(framework);

        TagsDTO tool = new TagsDTO();
        tool.setCategoryName("工具");
        tool.setTags(new ArrayList(redisHelper.sGet(TAGS_TOOL.getPrefix())));
        result.add(tool);

        TagsDTO db = new TagsDTO();
        db.setCategoryName("数据库");
        db.setTags(new ArrayList(redisHelper.sGet(TAGS_DB.getPrefix())));
        result.add(db);

        TagsDTO other = new TagsDTO();
        other.setCategoryName("其他");
        other.setTags(new ArrayList(redisHelper.sGet(TAGS_OTHER.getPrefix())));
        result.add(other);

        return result;
    }

}
