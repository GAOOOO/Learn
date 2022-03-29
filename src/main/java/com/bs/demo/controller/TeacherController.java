package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.service.ITeacherService;
import com.bs.demo.entity.Teacher;


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
 * @since 2022-03-27
 */
@Controller
@RequestMapping("/teacher")
@Api(tags = "")
public class TeacherController {

    @Autowired
    public ITeacherService iTeacherService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('teacher:list')")
    public Result teacherList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        Page<Teacher> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("teacher_name", searchOption.getSearchText());
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iTeacherService.page(page, queryWrapper));
    }

    @OperationLog("添加")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加")
    @PreAuthorize("hasAnyAuthority('teacher:add')")
    @Transactional
    public Result teacherAdd(@RequestBody @Valid Teacher teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iTeacherService.addTeacher(teacher);
        return result;
    }


    @OperationLog("修改")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAnyAuthority('teacher:edit')")
    @Transactional
    public Result teacherEdit(@RequestBody @Valid Teacher teacher,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iTeacherService.updateTeacher(teacher);
        return result;
    }



    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('teacher:del')")
    @Transactional
    public Result deleteTeacher(@RequestBody List<Teacher> teacherList) {
        Result result = iTeacherService.deleteTeacher(teacherList);
        return result;
    }
}
