package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.service.ILibraryClassService;
import com.bs.demo.entity.LibraryClass;


import org.springframework.validation.BindingResult;
import com.bs.demo.annotation.OperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author gf
 * @since 2022-03-09
 */
@Controller
@RestController
@RequestMapping("/libraryClass")
@Api(tags = "")
public class LibraryClassController {

    @Autowired
    public ILibraryClassService iLibraryClassService;


    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('libraryclass:list')")
    public Result libraryClassList(@RequestBody SearchOption searchOption) {
        QueryWrapper<LibraryClass> queryWrapper = new QueryWrapper<>();
        Page<LibraryClass> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("class_info", searchOption.getSearchText());
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iLibraryClassService.page(page, queryWrapper));
    }

    @OperationLog("添加")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加")
    @PreAuthorize("hasAnyAuthority('libraryclass:add')")
    @Transactional
    public Result libraryClassAdd(@RequestBody @Valid LibraryClass libraryClass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iLibraryClassService.addLibraryClass(libraryClass);
        return result;
    }


    @OperationLog("修改")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAnyAuthority('libraryclass:edit')")
    @Transactional
    public Result libraryClassEdit(@RequestBody @Valid LibraryClass libraryClass,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iLibraryClassService.updateLibraryClass(libraryClass);
        return result;
    }



    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('libraryclass:del')")
    @Transactional
    public Result deleteLibraryClass(@RequestBody List<LibraryClass> libraryClassList) {
        Result result = iLibraryClassService.deleteLibraryClass(libraryClassList);
        return result;
    }
}
