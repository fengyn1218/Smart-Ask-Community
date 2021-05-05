package com.feng.community.controller;

import com.feng.community.constant.PageConstant;
import com.feng.community.dto.TalkQueryDTO;
import com.feng.community.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fengyunan
 * Created on: 2021-05-04
 */
@Controller
public class TalkController {

    @GetMapping("/talk")
    public String tIndex() {
        return "t/index";
    }

    @ResponseBody
    @GetMapping(value = "/list")
    public Object commentList(
            HttpServletRequest request
            , @RequestParam(value = "id", required = false) Long id
            , @RequestParam(value = "type", required = false) Integer type
            , @RequestParam(value = "creator", required = false) Long creator
            , @RequestParam(value = PageConstant.PAGE_NUM, required = false, defaultValue = PageConstant.PAGE_NUM_DEFAULT) Integer page
            , @RequestParam(value = PageConstant.PAGE_SIZE, required = false, defaultValue = PageConstant.PAGE_SIZE_DEFAULT) Integer size
            , @RequestParam(value = PageConstant.SORT, required = false, defaultValue = PageConstant.PAGE_SORT_DEFAULT) String sort
            , @RequestParam(value = PageConstant.ORDER, required = false, defaultValue = PageConstant.PAGE_ORDER_DEFAULT) String order
    ) {
        TalkQueryDTO talkQueryDTO = new TalkQueryDTO();
        talkQueryDTO.setPage(page);
        talkQueryDTO.setCreator(creator);
        talkQueryDTO.setId(id);
        talkQueryDTO.setSize(size);
        talkQueryDTO.setType(type);
        talkQueryDTO.setSort(sort);
        talkQueryDTO.setOrder(order);
        talkQueryDTO.convert();
//        return ResultDTO.okOf(talkService.list(talkQueryDTO, null));
        return null;
    }
}
