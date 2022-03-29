package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.dto.LoginUserDto;
import com.bs.demo.service.IUserClassService;
import com.bs.demo.entity.UserClass;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import com.bs.demo.annotation.OperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author gf
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/userClass")
@Api(tags = "")
public class UserClassController {

    @Autowired
    public IUserClassService iUserClassService;


    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('userclass:list')")
    public Result userClassList(@RequestBody SearchOption searchOption) {
        QueryWrapper<UserClass> queryWrapper = new QueryWrapper<>();
        Page<UserClass> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        queryWrapper.eq("user_id",loginUserDto.getUser().getUserId());
        queryWrapper.like("class_info", searchOption.getSearchText());
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iUserClassService.page(page, queryWrapper));
    }

    @OperationLog("添加")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加")
    @PreAuthorize("hasAnyAuthority('userclass:add')")
    @Transactional
    public Result userClassAdd(@RequestBody @Valid UserClass userClass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iUserClassService.addUserClass(userClass);
        return result;
    }


    @OperationLog("修改")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAnyAuthority('userclass:edit')")
    @Transactional
    public Result userClassEdit(@RequestBody @Valid UserClass userClass,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iUserClassService.updateUserClass(userClass);
        return result;
    }



    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('userclass:del')")
    @Transactional
    public Result deleteUserClass(@RequestBody List<UserClass> userClassList) {
        Result result = iUserClassService.deleteUserClass(userClassList);
        return result;
    }
}
