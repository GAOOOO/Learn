package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.service.ITopicService;
import com.bs.demo.entity.Topic;


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
@RequestMapping("/topic")
@Api(tags = "")
public class TopicController {

    @Autowired
    public ITopicService iTopicService;


    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAnyAuthority('topic:list')")
    public Result topicList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
        Page<Topic> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.eq("library_id",searchOption.getSearchText());
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iTopicService.page(page, queryWrapper));
    }

    @OperationLog("添加")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加")
    @PreAuthorize("hasAnyAuthority('topic:add')")
    @Transactional
    public Result topicAdd(@RequestBody @Valid Topic topic, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iTopicService.addTopic(topic);
        return result;
    }


    @OperationLog("修改")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAnyAuthority('topic:edit')")
    @Transactional
    public Result topicEdit(@RequestBody @Valid Topic topic,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iTopicService.updateTopic(topic);
        return result;
    }



    @OperationLog("删除")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除")
    @PreAuthorize("hasAnyAuthority('topic:del')")
    @Transactional
    public Result deleteTopic(@RequestBody List<Topic> topicList) {
        Result result = iTopicService.deleteTopic(topicList);
        return result;
    }
}
