package com.feng.community.dto;

import com.feng.community.entity.TbUser;
import lombok.Data;

import java.util.List;

/**
 * @author: fengyunan
 * Created on: 2021-04-05
 */
@Data
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long created;
    private Long updated;
    private String updatedStr;
    private Long authorId;
    private Long viewCount;
    private Long commentCount;
    private Integer likeCount;
    private TbUser user;
    private List<CommentDTO> comments;

    private Long gmtLatestComment;
    private String gmtLatestCommentStr;
    private String shortDescription;
    private Integer isVisible;
    private Long status;
    private Integer type;
    private Integer permission;//阅读权限


    //状态
    private boolean approved = true;//是否合法（通过审核）
    private boolean sticky = false;//是否置顶
    private boolean essence = false;//是否加精
    private boolean favorite = false;//是否收藏
    private boolean edited = false;//是否编辑过
    private boolean paid = false;//是否收费
    private boolean canReply = true;//能否回复
    private boolean canView = true;//能否查看
    private boolean canFavorite = true;//能否收藏
    private boolean canSticky = false;//能否置顶，最高权限标志
    private boolean canEssence = false;//能否加精
    private boolean canDelete = false;//能否删除
    private boolean canPromote = false;//能否提升
    private boolean canClassify = false;//能否修改分类
    private boolean canEdit = false;//能否编辑
}
