package com.feng.community.service.like.impl;

import com.feng.community.dao.TbLikeMapper;
import com.feng.community.entity.TbLike;
import com.feng.community.entity.TbUser;
import com.feng.community.service.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: fengyunan
 * Created on: 2021-04-21
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private TbLikeMapper tbLikeMapper;

    @Override
    public boolean isLiked(TbUser user, Long postId) {
        Example example = new Example(TbLike.class);
        example.createCriteria().andEqualTo("liker", user.getId());
        List<TbLike> tbLikes = tbLikeMapper.selectByExample(example);
        if (tbLikes.size() != 0) {
            for (int i = 0; i < tbLikes.size(); i++) {
                if (tbLikes.get(i).getTargetId().equals(postId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
