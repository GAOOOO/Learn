package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.ICommentService;
import com.bs.demo.entity.Comment;
import com.bs.demo.mapper.CommentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author gf
 * @since 2021-12-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public Result addComment(Comment comment) {
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateComment(Comment comment) {
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteComment(List<Comment> commentList) {
        List<String> ids = commentList.stream().map(Comment::getPostsId).collect(Collectors.toList());
        commentMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}