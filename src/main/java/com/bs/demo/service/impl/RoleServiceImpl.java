package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleMenu;
import com.bs.demo.mapper.RoleMapper;
import com.bs.demo.mapper.RoleMenuMapper;
import com.bs.demo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gf
 * @since 2021-12-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Role getRoleById(Integer roleId) {
        return roleMapper.getRoleById(roleId);
    }

    @Override
    public Result addRole(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        Role roleCheck = roleMapper.selectOne(queryWrapper.eq("role_name", role.getRoleName()));
        if (roleCheck == null) {
            Role newRole = new Role();
            newRole.setRoleName(role.getRoleName());
            newRole.setDescription(role.getDescription());
            newRole.setCreateTime(LocalDateTime.now());
            if (roleMapper.insert(newRole) == 1) {
                Role newRole1 = roleMapper.selectOne(queryWrapper.eq("role_name", role.getRoleName()));

                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(newRole1.getRoleId());
                roleMenu.setMenuId(1);
                roleMenuMapper.insert(roleMenu);
                return Result.success().message("添加成功！").data(role.getDescription());
            } else {
                return Result.error().message("添加失败！").data(role.getDescription());
            }
        } else {
            return Result.error().message("已存在！").data(role.getDescription());
        }
    }

    @Override
    public Result updateRole(Role role) {
        Role orRole = roleMapper.selectById(role);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        Role role1 = roleMapper.selectOne(queryWrapper.eq("role_name", role.getRoleName()));
        if (orRole == null) {
            return Result.error().message("修改角色错误：【不存在该角色】").data(role);
        }else {
            //判断用户名是否存在
            if (role1 != null && !role.getRoleId().equals(role1.getRoleId())) {
                return Result.error().message("修改角色错误：【该角色已存在】");
            }
            int result = roleMapper.updateById(role);
            if (result == 1) {
                return Result.success().message("修改角色成功").data(role.getRoleName());
            } else {
                return Result.error().message("修改角色信息失败");
            }
        }

    }

    @Override
    public Result deleteRole(List<Role> roleList) {
        //对删除角色进行判断
        for (Role role : roleList) {
            if (role.getRoleId() == 1) {
                return Result.error().code(ResultCode.UNAUTHORIZED).message("禁止删除最高权限用户!");
            }
        }
        //统计要删除的ID
        List<Integer> ids = roleList.stream().map(Role::getRoleId).collect(Collectors.toList());
        roleMapper.deleteBatchIds(ids);
        roleMenuMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}
