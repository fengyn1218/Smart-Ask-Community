package com.feng.community.task;

import com.feng.community.dao.TbPostMapper;
import com.feng.community.entity.TbPost;
import com.feng.community.storage.TopPostsCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fengyunan
 * Created on: 2021-05-03
 */
@Component
public class TopPostsTask {
    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private TopPostsCache topPostsCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 2) // 每两小时执行一次
    public void topPostsTask() {
        int offset = 0;
        int limit = 20;
        // 每次扫表数目
        List<TbPost> list = new ArrayList<>();
        // 结果
        Map<String, Integer> priorities = new HashMap<>();
        Example example = new Example(TbPost.class);
        while (offset == 0 || list.size() == limit) {
            list = tbPostMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, limit));
            for (TbPost tbPost : list) {
                // 热度计算规则：收藏数*3+评论数*2+点击数
                topPostsCache.updateTopPosts(tbPost.getId(), tbPost.getLikeCount() * 3 + tbPost.getCommentCount() * 2 + tbPost.getViewCount());
            }
            offset += limit;
        }
    }
}
