package com.feng.community.controller;

import com.feng.community.annotation.NeedLoginToken;
import com.feng.community.constant.PageConstant;
import com.feng.community.dto.CommentQueryDTO;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fengyunan
 * Created on: 2021-04-09
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @GetMapping("/list")
    public ResultView list(@RequestParam(value = "id", required = false) Long id
            , @RequestParam(value = "postId", required = false) Long postId
            , @RequestParam(value = "type", required = false) Integer type
            , @RequestParam(value = "authorId", required = false) Long authorId
            , @RequestParam(value = PageConstant.PAGE_NUM, required = false, defaultValue = PageConstant.PAGE_NUM_DEFAULT) Integer page
            , @RequestParam(value = PageConstant.PAGE_SIZE, required = false, defaultValue = PageConstant.PAGE_SIZE_DEFAULT) Integer size
            , @RequestParam(value = PageConstant.SORT, required = false, defaultValue = PageConstant.PAGE_SORT_DEFAULT) String sort
            , @RequestParam(value = PageConstant.ORDER, required = false, defaultValue = PageConstant.PAGE_ORDER_DEFAULT) String order) {
        CommentQueryDTO commentQueryDTO = new CommentQueryDTO();
        commentQueryDTO.setPage(page);
        commentQueryDTO.setAuthorId(authorId);
        commentQueryDTO.setId(id);
        commentQueryDTO.setPostId(postId);
        commentQueryDTO.setSize(size);
        commentQueryDTO.setType(type);
        commentQueryDTO.setSort(sort);
        commentQueryDTO.setOrder(order);
        commentQueryDTO.convert();
        return ResultView.success(commentService.getCommentList(commentQueryDTO));
    }

    @NeedLoginToken
    @ResponseBody
    @PostMapping("publish")
    public ResultView publish(@RequestParam(value = "postId", required = true) Long postId,
                              @RequestParam(value = "content", required = true) String content,
                              @RequestParam(value = "isReComment", required = false, defaultValue = "false") Boolean isReComment,
                              HttpServletRequest request
    ) {
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");
        return commentService.publish(postId, loginUser.getId(), content, isReComment);
    }

    @NeedLoginToken
    @ResponseBody
    @PostMapping("delete")
    public ResultView delete(@RequestParam(value = "postId", required = true) Long postId,
                             @RequestParam(value = "commentId", required = true) Long commentId,
                             HttpServletRequest request) {
        TbUser loginUser = (TbUser) request.getAttribute("loginUser");
        return commentService.delete(postId, loginUser.getId(), commentId);
    }

    @ResponseBody
    @GetMapping(value = "/list/{id}")
    public ResultView comments(@PathVariable(name = "id") Long id) {
        return ResultView.success(commentService.listByTargetId(id));
    }

}
