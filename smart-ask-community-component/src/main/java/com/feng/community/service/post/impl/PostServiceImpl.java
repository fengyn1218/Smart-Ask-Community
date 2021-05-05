package com.feng.community.service.post.impl;

import com.feng.community.constant.ResultViewCode;
import com.feng.community.dao.TbCommentMapper;
import com.feng.community.dao.TbLikeMapper;
import com.feng.community.dao.TbPostMapper;
import com.feng.community.dao.TbUserMapper;
import com.feng.community.dto.CommentDTO;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.dto.PostDTO;
import com.feng.community.entity.TbComment;
import com.feng.community.entity.TbLike;
import com.feng.community.entity.TbPost;
import com.feng.community.entity.TbUser;
import com.feng.community.exception.CustomizeException;
import com.feng.community.service.comment.CommentService;
import com.feng.community.service.like.LikeService;
import com.feng.community.service.post.PostService;
import com.feng.community.service.user.UserInfoService;
import com.feng.community.storage.HotPostCache;
import com.feng.community.storage.HotTagCache;
import com.feng.community.storage.TopPostsCache;
import com.feng.community.storage.ZeroCommentPostCache;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.feng.community.constant.LikeConstant.TYPE_COLLECTION;
import static com.feng.community.constant.PageConstant.PAGE_ORDER_DEFAULT;
import static com.feng.community.constant.PostConstant.*;
import static com.feng.community.constant.TimeConstant.FORMAT;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private TbPostMapper tbPostMapper;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbLikeMapper tbLikeMapper;
    @Autowired
    private TbCommentMapper tbCommentMapper;

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private TopPostsCache topPostsCache;
    @Autowired
    private HotTagCache hotTagCache;
    @Autowired
    private ZeroCommentPostCache zeroCommentPostCache;
    @Autowired
    private HotPostCache hotPostCache;


    @Override
    public List<PostDTO> getTopPost(String search, String tag, String sort, Integer type) {
        // 从redis获取topPosts
        List<TbPost> posts = topPostsCache.getTopPosts().stream().map(e -> {
            Long postId = Long.valueOf(String.valueOf(e));
            Example example = new Example(TbPost.class);
            example.createCriteria().andEqualTo("id", postId);
            List<TbPost> tbPosts = tbPostMapper.selectByExample(example);
            return tbPosts.get(0);
        }).collect(Collectors.toList());
        // 处理搜索
        if (!StringUtils.isBlank(search)) {
            for (int i = 0; i < posts.size(); i++) {
                TbPost tbPost = posts.get(i);
                if (!tbPost.getTitle().contains(search)) {
                    posts.remove(tbPost);
                    i--;
                }
            }
        }
        // 处理标签
        if (!StringUtils.isBlank(tag)) {
            for (int i = 0; i < posts.size(); i++) {
                TbPost tbPost = posts.get(i);
                String[] tags = StringUtils.split(tbPost.getTag(), ",");
                if (!ArrayUtils.contains(tags, tag)) {
                    posts.remove(tbPost);
                    i--;
                }
            }
        }
        // 处理type
        if (type != null) {
            for (int i = 0; i < posts.size(); i++) {
                TbPost tbPost = posts.get(i);
                if (!tbPost.getType().equals(Long.valueOf(type))) {
                    posts.remove(tbPost);
                    i--;
                }
            }
        }
        return posts.stream().map(e -> {
            PostDTO postDTO = new PostDTO();
            TbUser user = tbUserMapper.selectByPrimaryKey(e.getAuthorId());
            BeanUtils.copyProperties(e, postDTO);
            postDTO.setUser(user);
            postDTO.setUpdatedStr(DateFormatUtils.format(postDTO.getUpdated(), FORMAT));
            return postDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO getPostByType(String search, String tag, String sort, Integer page, Integer size, Integer type) {
        PaginationDTO<PostDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage = 0;
        Example example = new Example(TbPost.class);
        Example.Criteria criteria = example.createCriteria();
        if (type != null) {
            criteria.andEqualTo("type", type);
        }
        if (search != null && !search.equals("")) {
            criteria.andLike("title", search);
        }

        List<Long> postIds = null;

        if (sort != null) {
            switch (sort) {
                case HOT7:
                    postIds = hotPostCache.getHot7Posts()
                            .stream()
                            .map(e -> Long.valueOf(String.valueOf(e)))
                            .collect(Collectors.toList());
                    break;
                case HOT30:
                    postIds = hotPostCache.getHot30Posts()
                            .stream()
                            .map(e -> Long.valueOf(String.valueOf(e)))
                            .collect(Collectors.toList());
                    break;
                case NO:
                    postIds = zeroCommentPostCache.getZeroCommentPosts()
                            .stream()
                            .map(e -> Long.valueOf(String.valueOf(e)))
                            .collect(Collectors.toList());
                    break;
            }
        }
        List<TbPost> tbPosts = new ArrayList<>();
        List<PostDTO> postDTOList = null;
        int totalCount = 0;
        // 榜单帖子,从缓存里拿
        if (postIds != null) {
            List<TbPost> result = new ArrayList<>();
            List<TbPost> tbPosts1 = tbPostMapper.selectByExample(example);
            for (TbPost tbPost : tbPosts1) {
                for (Long postId : postIds) {
                    if (tbPost.getId().equals(postId)) {
                        tbPosts.add(tbPost);
                    }
                }
            }
            totalCount = tbPosts.size();

            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }
            int start = page < 1 ? 0 : size * (page - 1);
            if (totalCount < size) {
                result = tbPosts;
            } else if (totalCount % size == 0) {
                for (int i = start; i < size; i++) {
                    result.add(tbPosts.get(i));
                }
            } else {
                if (page.equals(totalPage)) {
                    for (int i = start; i < totalCount - size * (totalPage - 1); i++) {
                        result.add(tbPosts.get(i));
                    }
                } else {
                    for (int i = start; i < size; i++) {
                        result.add(tbPosts.get(i));
                    }
                }
            }


            postDTOList = result.stream().map(e -> {
                PostDTO postDTO = new PostDTO();
                BeanUtils.copyProperties(e, postDTO);
                TbUser tbUser = tbUserMapper.selectByPrimaryKey(e.getAuthorId());
                postDTO.setUser(tbUser);
                return postDTO;
            }).collect(Collectors.toList());

        }
        // 非榜单
        else {
            totalCount = tbPostMapper.selectCountByExample(example);

            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }

            Integer offset = page < 1 ? 0 : size * (page - 1);
            tbPosts = tbPostMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, size));

            postDTOList = tbPosts.stream().map(e -> {
                PostDTO postDTO = new PostDTO();
                BeanUtils.copyProperties(e, postDTO);
                TbUser tbUser = tbUserMapper.selectByPrimaryKey(e.getAuthorId());
                postDTO.setUser(tbUser);
                return postDTO;
            }).collect(Collectors.toList());

        }

        paginationDTO.setData(postDTOList);
        paginationDTO.setPagination(totalPage, page);
        return paginationDTO;
    }

    @Override
    public PaginationDTO<TbPost> listByUserId(Long userId, Integer page, Integer size) {
        Integer totalPage;
        Example example = new Example(TbPost.class);
        example.createCriteria().andEqualTo("authorId", userId);
        Integer totalCount = tbPostMapper.selectCountByExample(example);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        Integer offset = size * (page - 1);
        example.setOrderByClause("updated desc");
        List<TbPost> posts = tbPostMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, size));

        List<PostDTO> postDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (TbPost question : posts) {
            TbUser user = tbUserMapper.selectByPrimaryKey(question.getAuthorId());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(question, postDTO);
            postDTO.setUser(user);
            postDTO.setUpdatedStr(DateFormatUtils.format(postDTO.getUpdated(), FORMAT));
            postDTOList.add(postDTO);
        }
        paginationDTO.setData(postDTOList);
        paginationDTO.setTotalCount(totalCount);
        paginationDTO.setPagination(totalPage, page);
        return paginationDTO;
    }

    @Override
    public PostDTO getPostById(Long postId, HttpServletRequest request) {
        //帖子信息
        TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
        List<CommentDTO> collect = commentService.getCommentByPostId(postId).stream().map(e -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(e, commentDTO);
            commentDTO.setCreatedStr(DateFormatUtils.format(e.getCreated(), FORMAT));
            commentDTO.setUser(userInfoService.selectUserByUserId(String.valueOf(e.getAuthorId())));
            return commentDTO;
        }).collect(Collectors.toList());
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(tbPost, postDTO);
        postDTO.setUser(userInfoService.selectUserByUserId(tbPost.getAuthorId().toString()));
        postDTO.setUpdatedStr(DateFormatUtils.format(postDTO.getUpdated(), FORMAT));
        postDTO.setViewCount(tbPost.getViewCount());
        postDTO.setCommentCount(Long.valueOf(tbPost.getCommentCount().toString()));
        postDTO.setComments(collect);

        //登录用户信息
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");
        //是否关注、收藏
        postDTO.setFavorite(likeService.isLiked(loginUser, postId));
        // 是否可以编辑、删除
        if (loginUser.getId().equals(tbPost.getAuthorId())) {
            postDTO.setCanEdit(true);
            postDTO.setCanDelete(true);
            postDTO.setEssence(true);
        }
        tbPost.setViewCount(tbPost.getViewCount() + VIEW_COUNT_STEP);
        tbPostMapper.updateByPrimaryKey(tbPost);
        return postDTO;
    }

    public PaginationDTO listByExample(Long userId, Integer page, Integer size, String likes) {
        Integer totalPage;
        Example example = new Example(TbLike.class);
        example.createCriteria()
                .andEqualTo("liker", userId)
                .andEqualTo("type", TYPE_COLLECTION); // 帖子收藏

        int totalCount = tbLikeMapper.selectCountByExample(example);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        if (page < 1) {
            page = 1;
        }

        Integer offset = size * (page - 1);
        example.setOrderByClause("created" + " " + PAGE_ORDER_DEFAULT);
        List<TbLike> tbLikes = tbLikeMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, 10));

        List<TbPost> tbPostList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        List<PostDTO> postDTOList = new ArrayList<>();
        TbPost post;
        for (TbLike tbLike : tbLikes) {
            TbPost tbPost = tbPostMapper.selectByPrimaryKey(tbLike.getTargetId());
            if (tbPost != null)
                tbPostList.add(tbPost);
        }

        for (TbPost tbPost : tbPostList) {
            TbUser tbUser = tbUserMapper.selectByPrimaryKey(tbPost.getAuthorId());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(tbPost, postDTO);
            postDTO.setUser(tbUser);
            postDTO.setUpdatedStr(DateFormatUtils.format(postDTO.getUpdated(), FORMAT));
            postDTOList.add(postDTO);
        }

        paginationDTO.setData(postDTOList);
        paginationDTO.setTotalCount(totalCount);
        paginationDTO.setPagination(totalPage, page);
        return paginationDTO;
    }

    @Override
    public PostDTO getById(Long id, TbUser user) {
        TbPost post = tbPostMapper.selectByPrimaryKey(id);
        if (post == null) {
            throw new CustomizeException(ResultViewCode.QUESTION_NOT_FOUND);
        }
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        TbUser tbUser = tbUserMapper.selectByPrimaryKey(post.getAuthorId());

        postDTO.setUser(tbUser);
        postDTO = setStatuses(postDTO, 0L, user);
        postDTO.setUpdatedStr(DateFormatUtils.format(postDTO.getUpdated(), FORMAT));
        return postDTO;
    }

    @Override
    public int delPostById(Long userId, Long postId) {
        int c = 0;
        TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
        if (tbPost.getAuthorId().equals(userId)) {
            c = tbPostMapper.deleteByPrimaryKey(postId);
            // 同步redis
            if (c != 0) {
                // 移除topPost
                topPostsCache.delTopPost(postId);
                String[] split = tbPost.getTag().split(",");
                for (int i = 0; i < split.length; i++) {
                    // 移除hotTags
                    hotTagCache.delHotTag(split[i]);
                }
            }
            // 删除评论
            Example example = new Example(TbComment.class);
            example.createCriteria().andEqualTo("postId", postId);
            List<TbComment> tbComments = tbCommentMapper.selectByExample(example);
            tbComments.forEach(e -> {
                Example example1 = new Example(TbComment.class);
                example1.createCriteria().andEqualTo("postId", e.getId());
                if (tbCommentMapper.selectByExample(example1).size() != 0) {
                    // 删除二级评论
                    tbCommentMapper.deleteByExample(example1);
                }
            });
            // 删除收藏信息
            Example example1 = new Example(TbLike.class);
            example1.createCriteria().andEqualTo("targetId", postId);
            tbLikeMapper.deleteByExample(example1);

            tbCommentMapper.deleteByExample(example);
        }
        return c;
    }

    @Override
    public List<PostDTO> getRelatedPosts(Long postId) {
        TbPost post = tbPostMapper.selectByPrimaryKey(postId);

        Example example = new Example(TbPost.class);
        example.createCriteria().andEqualTo("authorId", post.getAuthorId());

        return tbPostMapper.selectByExample(example).stream().map(e -> {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(e, postDTO);
            TbUser tbUser = tbUserMapper.selectByPrimaryKey(post.getAuthorId());
            postDTO.setUser(tbUser);
            return postDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public long getPostTypeById(long postId) {
        TbPost tbPost = tbPostMapper.selectByPrimaryKey(postId);
        // 9为兼容二级评论的类型
        return Optional.ofNullable(tbPost).map(TbPost::getType).orElse(9L);
    }

    private PostDTO setStatuses(PostDTO postDTO, Long viewUser_id, TbUser user) {
        // 是否能编辑过
        postDTO.setEdited(postDTO.getAuthorId().equals(user.getId()));
        // 加精
        if (postDTO.getStatus() == 3) postDTO.setEssence(true);
        // 置顶
        if (postDTO.getStatus() == 2) postDTO.setSticky(true);

        if (viewUser_id == 0L) {
            boolean liked = likeService.isLiked(user, postDTO.getId());
            postDTO.setFavorite(liked);
            if (postDTO.getAuthorId().equals(user.getId())) {
                postDTO.setCanEdit(true);
                postDTO.setCanClassify(true);
                postDTO.setCanDelete(true);
            }
        }

        return postDTO;
    }

}
