package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.dto.LoginUserDto;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleUser;
import com.bs.demo.entity.User;
import com.bs.demo.entity.vo.RoleUserVo;
import com.bs.demo.mapper.RoleMapper;
import com.bs.demo.mapper.RoleUserMapper;
import com.bs.demo.mapper.UserMapper;
import com.bs.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result addUser(RoleUserVo roleUserVo) {
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User userCheck = userMapper.selectOne(queryWrapper.eq("user_name", roleUserVo.getUserName()));
        if (userCheck == null) {
            User user = new User();
            user.setUserName(roleUserVo.getUserName());
            user.setNickName(roleUserVo.getNickName());
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            user.setSex(roleUserVo.getSex());
            user.setStatus(roleUserVo.getStatus());
            user.setEmail(roleUserVo.getEmail());
            user.setPhone(roleUserVo.getPhone());
            user.setCreateUser(loginUserDto.getUsername());
            user.setCreateTime(LocalDateTime.now());
            user.setAvatar("/assets/admin/avatar.gif");
            if (userMapper.insert(user) == 1) {
                User newUser = userMapper.selectOne(queryWrapper.eq("user_name", user.getUserName()));
                Role role = roleMapper.selectById(roleUserVo.getRoleId());
                if (role == null){
                    return Result.error().message("角色数据不存在，添加失败！");
                }else {
                    RoleUser roleUser = new RoleUser();
                    roleUser.setRoleId(role.getRoleId());
                    roleUser.setUserId(newUser.getUserId());
                    if (roleUserMapper.insert(roleUser) == 1) {
                        return Result.success().message("添加成功！").data(newUser.getUserName());
                    } else {
                        return Result.error().message("角色添加失败！").data(newUser.getUserName());
                    }
                }
            } else {
                return Result.error().message("添加失败！").data(user.getUserName());
            }
        } else {
            return Result.error().message("已存在！").data(roleUserVo.getUserName());
        }
    }



    @Override
    public Result updateUser(RoleUserVo tbUser) {
        User user = userMapper.selectById(tbUser.getUserId());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user1 = userMapper.selectOne(queryWrapper.eq("user_name", tbUser.getUserName()));
        //判断是否存在该用户ID
        if (user == null) {
            return Result.error().message("修改用户错误：【不存在该用户】").data(tbUser);
        }else {
            //判断用户名是否存在
            if (user1 != null && !user.getUserId().equals(user1.getUserId())) {
                return Result.error().message("修改用户错误：【该用户名已存在】");
            }else {
                //执行更新操作
                LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                user.setUserName(tbUser.getUserName());
                user.setNickName(tbUser.getNickName());
                user.setSex(tbUser.getSex());
                user.setPhone(tbUser.getPhone());
                user.setEmail(tbUser.getEmail());
                user.setStatus(tbUser.getStatus());
                user.setUpdateUser(loginUserDto.getUsername());
                int result = userMapper.updateById(user);
                if (tbUser.getRoleId() != null){
                    RoleUser roleUser = roleUserMapper.selectById(tbUser.getUserId());
                    roleUser.setRoleId(tbUser.getRoleId());
                    roleUserMapper.updateById(roleUser);
                }
                if (result == 1) {
                    return Result.success().message("修改用户成功").data(user.getUserName());
                } else {
                    return Result.error().message("修改用户信息失败");
                }
            }
        }
    }

    @Override
    public Result deleteUser(List<User> userList) {
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取操作者权限
        Integer roleId = loginUserDto.getRoleList().get(0).getRoleId();
        //统计要删除的ID
        List<Integer> ids = userList.stream().map(User::getUserId).collect(Collectors.toList());
        QueryWrapper<RoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", ids);
        //多值查询ID对应的权限
        List<RoleUser> roleUsers = roleUserMapper.selectList(queryWrapper);
        boolean delSign = true;
        User wrong = new User();
        //对删除角色进行判断
        for (RoleUser roleUser : roleUsers) {
            if (roleUser.getRoleId() <= roleId && roleUser.getUserId() != 1) {
                wrong = userList.stream()
                        .filter(u -> u.getUserId().equals(roleUser.getUserId()))
                        .findAny()
                        .orElse(null);
                delSign = false;
            } else if (roleUser.getUserId() == 1) {
                return Result.error().code(ResultCode.UNAUTHORIZED).message("禁止删除最高权限用户!");
            }
        }
        //如果是超级管理员
        if (loginUserDto.getUser().getUserId() == 1) {
            int resultCode = userMapper.deleteBatchIds(ids);
            roleUserMapper.deleteBatchIds(ids);
            if (resultCode == 1) {
                return Result.success().message("删除成功");
            } else {
                return Result.error().message("删除失败");
            }
        } else {
            if (delSign) {
                int resultCode = userMapper.deleteBatchIds(ids);
                int resultCode1 = roleUserMapper.deleteBatchIds(ids);
                if (resultCode==1 && resultCode1==1) {
                    return Result.success().message("删除成功！");
                } else {
                    return Result.error().message("删除失败！");
                }
            } else {
                return Result.error().code(ResultCode.UNAUTHORIZED).message("权限不足!").data(wrong);
            }
        }
    }

    @Override
    public Result personalEdit(User user) {
        User loginUser = SecurityUtils.getCurrentUser().getUser();
        loginUser.setPhone(user.getPhone());
        loginUser.setEmail(user.getEmail());
        loginUser.setNickName(user.getNickName());
        loginUser.setSex(user.getSex());
        userMapper.updateById(loginUser);
        return Result.success().message("修改成功");
    }

}
