package com.feng.community.task;

import com.feng.community.dao.TbPostMapper;
import com.feng.community.entity.TbPost;
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
 * 定时扫表，更新Redis
 *
 * @author: fengyunan
 * Created on: 2021-04-29
 */
@Component
public class HotTagTask {

    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 2) // 每两小时执行一次
    public void hotTagSchedule() {
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
                String[] tags = StringUtils.split(tbPost.getTag(), ",");
                for (String tag : tags) {
                    priorities.merge(tag, 1, Integer::sum);
                }
            }
            offset += limit;
        }
        hotTagCache.updateHotTag(priorities);
    }

}
