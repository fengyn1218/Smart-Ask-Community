package com.feng.community.service.comment;

import com.feng.community.dto.CommentDTO;
import com.feng.community.dto.CommentQueryDTO;
import com.feng.community.dto.PaginationDTO;

public interface CommentService {
    PaginationDTO<CommentDTO> getCommentList(CommentQueryDTO commentQueryDTO);
}
