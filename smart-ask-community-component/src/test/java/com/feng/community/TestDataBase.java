//package com.feng.community;
//
//import java.util.List;
//
//import com.feng.community.dao.TbPostMapper;
//import com.feng.community.entity.TbPost;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.feng.community.dao.TbUserMapper;
//import com.feng.community.entity.TbUser;
//import tk.mybatis.mapper.entity.Example;
//
///**
// * @author fengyunan
// * Created on 2021-03-05
// */
//@SpringBootTest
//public class TestDataBase {
//
//    @Autowired
//    private TbUserMapper tbUserMapper;
//    @Autowired
//    private TbPostMapper tbPostMapper;
//
//    @Test
//    public void select() {
//        TbUser user = new TbUser();
//        user.setEmail("123");
//        user.setPassword("123");
//        user.setSex(1L);
//        user.setActivating(1);
//        user.setSignature("123");
//        int insert = tbUserMapper.insert(user);
//        System.out.println(insert);
//    }
//
//    @Test
//    public void post(){
//        Example example=new Example(TbPost.class);
//        List<TbPost> tbPosts = tbPostMapper.selectByExample(example);
//        System.out.println(tbPosts);
//    }
//
//    @Test
//    public void insert() {
//        TbPost tbPost = new TbPost();
//        tbPost.setType(1L);
//        tbPost.setCreated(System.currentTimeMillis());
//        tbPost.setUpdated(System.currentTimeMillis());
//        tbPost.setStatus(1L);
//        tbPost.setAuthorId(1L);
//        tbPost.setTag("123");
//        tbPost.setPermission(1);
//        tbPost.setLikeCount(0);
//        tbPost.setViewCount(0L);
//        tbPost.setTitle("123");
//        tbPost.setDescription("123");
//
//        tbPostMapper.insert(tbPost);
//
//    }
//}
