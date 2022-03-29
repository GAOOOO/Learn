package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.UserClass;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface IUserClassService extends IService<UserClass> {

    Result addUserClass(UserClass userClass);

    Result updateUserClass(UserClass userClass);

    Result deleteUserClass(List<UserClass> userClassList);
}
