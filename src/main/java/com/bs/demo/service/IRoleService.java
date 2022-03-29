package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gf
 * @since 2021-12-28
 */
public interface IRoleService extends IService<Role> {

    Role getRoleById(Integer roleId);

    Result addRole(Role role);

    Result updateRole(Role role);

    Result deleteRole(List<Role> roleList);
}
