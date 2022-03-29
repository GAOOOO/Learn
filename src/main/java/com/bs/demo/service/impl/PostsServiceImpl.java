package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.IPostsService;
import com.bs.demo.entity.Posts;
import com.bs.demo.mapper.PostsMapper;

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
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

    @Autowired
    private PostsMapper postsMapper;


    @Override
    public Result addPosts(Posts posts) {
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updatePosts(Posts posts) {
        return Result.success().message("修改成功");
    }

    @Override
    public Result deletePosts(List<Posts> postsList) {
        List<Integer> ids = postsList.stream().map(Posts::getPostsId).collect(Collectors.toList());
        postsMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}