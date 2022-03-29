package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.dto.LoginUserDto;
import com.bs.demo.entity.LibraryClass;
import com.bs.demo.service.ILearnLibraryService;
import com.bs.demo.entity.LearnLibrary;


import com.bs.demo.service.ILibraryClassService;
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
 * @since 2022-03-10
 */
@Controller
@RequestMapping("/learnLibrary")
@Api(tags = "")
public class LearnLibraryController {

    @Autowired
    public ILearnLibraryService iLearnLibraryService;


    @Autowired
    public ILibraryClassService iLibraryClassService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('library:list')")
    public Result learnLibraryList(@RequestBody SearchOption searchOption) {
        QueryWrapper<LearnLibrary> queryWrapper = new QueryWrapper<>();
        Page<LearnLibrary> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("library_info", searchOption.getSearchText());
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        queryWrapper.eq("user_id",loginUserDto.getUser().getUserId());

        Page<LearnLibrary> page1 = iLearnLibraryService.page(page, queryWrapper);
        page1.getRecords().forEach(item -> {
            item.setLibraryClass(iLibraryClassService.getOne(new QueryWrapper<LibraryClass>()
                    .eq("class_id",item.getLibraryClass())).getClassInfo());
        });


        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(page1);
    }

    @OperationLog("添加")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加")
    @PreAuthorize("hasAnyAuthority('library:add')")
    @Transactional
    public Result learnLibraryAdd(@RequestBody @Valid LearnLibrary learnLibrary, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iLearnLibraryService.addLearnLibrary(learnLibrary);
        return result;
    }


    @OperationLog("修改")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAnyAuthority('library:edit')")
    @Transactional
    public Result learnLibraryEdit(@RequestBody @Valid LearnLibrary learnLibrary,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Result result = iLearnLibraryService.updateLearnLibrary(learnLibrary);
        return result;
    }



    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('library:del')")
    @Transactional
    public Result deleteLearnLibrary(@RequestBody List<LearnLibrary> learnLibraryList) {
        Result result = iLearnLibraryService.deleteLearnLibrary(learnLibraryList);
        return result;
    }
}
