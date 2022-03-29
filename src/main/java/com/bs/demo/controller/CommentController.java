package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.entity.Posts;
import com.bs.demo.entity.User;
import com.bs.demo.service.ICommentService;
import com.bs.demo.entity.Comment;


import com.bs.demo.service.IPostsService;
import com.bs.demo.service.IUserService;
import org.springframework.validation.BindingResult;
import com.bs.demo.annotation.OperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author gf
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/comment")
@Api(tags = "")
public class CommentController {

    @Autowired
    public ICommentService iCommentService;


    @Autowired
    public IUserService iUserService;


    @Autowired
    public IPostsService iPostsService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('comment:list')")
    public Result commentList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        Page<Comment> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("comment_info", searchOption.getSearchText());
        Page<Comment> page1 = iCommentService.page(page, queryWrapper);
        page1.getRecords().forEach(item -> {
            item.setUserId(iUserService.getOne(new QueryWrapper<User>()
                    .eq("user_id",item.getUserId())).getNickName());

            item.setPostsId(iPostsService.getOne(new QueryWrapper<Posts>()
                    .eq("posts_id",item.getPostsId())).getPostsTitle());
        });


        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(page1);
    }


    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('comment:del')")
    @Transactional
    public Result deleteComment(@RequestBody List<Comment> commentList) {
        Result result = iCommentService.deleteComment(commentList);
        return result;
    }
}
