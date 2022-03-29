package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface IPostsService extends IService<Posts> {

    Result addPosts(Posts posts);

    Result updatePosts(Posts posts);

    Result deletePosts(List<Posts> postsList);
}
