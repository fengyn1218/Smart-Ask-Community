package com.feng.community.service.comment;

import com.feng.community.dto.CommentDTO;
import com.feng.community.dto.CommentQueryDTO;
import com.feng.community.dto.PaginationDTO;
import com.feng.community.entity.TbComment;

import java.util.List;

public interface CommentService {
    PaginationDTO<CommentDTO> getCommentList(CommentQueryDTO commentQueryDTO);

    List<TbComment> getCommentByPostId(Long postId);
}
