package com.feng.community.task;

import com.feng.community.dao.TbPostMapper;
import com.feng.community.entity.TbPost;
import com.feng.community.storage.HotPostCache;
import com.feng.community.storage.HotTagCache;
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
 * Created on: 2021-05-05
 */
@Component
public class HotPostsTask {
    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private HotPostCache hotPostCache;

    @Scheduled(fixedRate = 1000 * 60 * 60) // 每一小时执行一次
    public void hot7PostSchedule() {
        int offset = 0;
        int limit = 20;
        // 每次扫表数目
        List<TbPost> list = new ArrayList<>();
        // 结果
        Map<Long, Long> priorities = new HashMap<>();
        Example example = new Example(TbPost.class);
        Long now = System.currentTimeMillis();
        example.createCriteria().andBetween("created", now - (100 * 60 * 60 * 24 * 7), now);
        while (offset == 0 || list.size() == limit) {
            list = tbPostMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, limit));
            for (TbPost tbPost : list) {
                // 热度计算规则（收藏数*3+评论数*2+观看数）
                priorities.put(tbPost.getId(), tbPost.getLikeCount() * 3 + tbPost.getCommentCount() * 2 + tbPost.getViewCount());
            }
            offset += limit;
        }
        hotPostCache.updateHot7Posts(priorities);
    }

    @Scheduled(fixedRate = 1000 * 60 * 60) // 每一小时执行一次
    public void hot30PostSchedule() {
        int offset = 0;
        int limit = 20;
        // 每次扫表数目
        List<TbPost> list = new ArrayList<>();
        // 结果
        Map<Long, Long> priorities = new HashMap<>();
        Example example = new Example(TbPost.class);
        Long now = System.currentTimeMillis();
        example.createCriteria().andBetween("created", now - (100 * 60 * 60 * 24 * 7), now);
        while (offset == 0 || list.size() == limit) {
            list = tbPostMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, limit));
            for (TbPost tbPost : list) {
                // 热度计算规则（收藏数*3+评论数*2+观看数）
                priorities.put(tbPost.getId(), tbPost.getLikeCount() * 3 + tbPost.getCommentCount() * 2 + tbPost.getViewCount());
            }
            offset += limit;
        }
        hotPostCache.updateHot30Posts(priorities);
    }
}
