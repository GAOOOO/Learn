package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface ICommentService extends IService<Comment> {

    Result addComment(Comment comment);

    Result updateComment(Comment comment);

    Result deleteComment(List<Comment> commentList);
}
