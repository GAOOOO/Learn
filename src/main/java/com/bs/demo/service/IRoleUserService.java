package com.bs.demo.service;

import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.demo.entity.vo.RoleUserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gf
 * @since 2021-12-30
 */
public interface IRoleUserService extends IService<RoleUser> {

    List<RoleUser> getRoleUserByUserId(Integer userId);


    Role getUserRole(Integer userId);
}
