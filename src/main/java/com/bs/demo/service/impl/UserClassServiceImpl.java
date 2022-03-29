package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.dto.LoginUserDto;
import com.bs.demo.service.IUserClassService;
import com.bs.demo.entity.UserClass;
import com.bs.demo.mapper.UserClassMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserClassServiceImpl extends ServiceImpl<UserClassMapper, UserClass> implements IUserClassService {

    @Autowired
    private UserClassMapper userClassMapper;


    @Override
    public Result addUserClass(UserClass userClass) {
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userClass.setUserId(loginUserDto.getUser().getUserId().toString());

        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateUserClass(UserClass userClass) {
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userClass.setUserId(loginUserDto.getUser().getUserId().toString());

        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteUserClass(List<UserClass> userClassList) {
        List<Integer> ids = userClassList.stream().map(UserClass::getClassId).collect(Collectors.toList());
        userClassMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}