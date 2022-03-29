package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.demo.entity.vo.RoleUserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gf
 * @since 2021-12-28
 */
public interface IUserService extends IService<User> {

    Result updateUser(RoleUserVo tbUser);

    Result addUser(RoleUserVo userVo);

    Result deleteUser(List<User> userList);

    Result personalEdit(User user);
}
