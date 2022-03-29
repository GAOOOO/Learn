package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.service.IAnswerService;
import com.bs.demo.entity.Answer;


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
@RequestMapping("/answer")
@Api(tags = "")
public class AnswerController {

    @Autowired
    public IAnswerService iAnswerService;


    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('answer:list')")
    public Result answerList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
        Page<Answer> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("answer_name", searchOption.getSearchText());
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iAnswerService.page(page, queryWrapper));
    }

    @OperationLog("添加")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加")
    @PreAuthorize("hasAnyAuthority('answer:add')")
    @Transactional
    public Result answerAdd(@RequestBody @Valid Answer answer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iAnswerService.addAnswer(answer);
        return result;
    }


    @OperationLog("修改")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAnyAuthority('answer:edit')")
    @Transactional
    public Result answerEdit(@RequestBody @Valid Answer answer,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iAnswerService.updateAnswer(answer);
        return result;
    }



    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('answer:del')")
    @Transactional
    public Result deleteAnswer(@RequestBody List<Answer> answerList) {
        Result result = iAnswerService.deleteAnswer(answerList);
        return result;
    }
}
