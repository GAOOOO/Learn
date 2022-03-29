package com.bs.demo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.service.INoticeService;
import com.bs.demo.entity.Notice;


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
 * @author gf
 * @since 2022-02-01
 */
@Controller
@RestController
@RequestMapping("/notice")
@Api(tags = "公告管理")
public class NoticeController {

    @Autowired
    public INoticeService iNoticeService;


    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "公告列表")
    @PreAuthorize("hasAnyAuthority('notice:list')")
    public Result noticeList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        Page<Notice> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("notice_title", searchOption.getSearchText()).or()
                .like("notice_content", searchOption.getSearchText()).or()
                .like("create_user", searchOption.getSearchText()).or()
                .like("update_user", searchOption.getSearchText())
                .orderByDesc("create_time");
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iNoticeService.page(page, queryWrapper));
    }

    @GetMapping("/all_notice")
    public Result getAllNotice() {
        List<Notice> notices = iNoticeService.list(new QueryWrapper<Notice>()
                .orderByDesc("create_time"));

        return Result.success().data(notices);
    }

    @OperationLog("添加公告")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加公告")
    @PreAuthorize("hasAnyAuthority('notice:add')")
    @Transactional
    public Result noticeAdd(@RequestBody @Valid Notice notice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iNoticeService.addNotice(notice);
        return result;
    }


    @OperationLog("修改公告")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改公告")
    @PreAuthorize("hasAnyAuthority('notice:edit')")
    @Transactional
    public Result noticeEdit(@RequestBody @Valid Notice notice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iNoticeService.updateNotice(notice);
        return result;
    }


    @OperationLog("删除公告")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除公告")
    @PreAuthorize("hasAnyAuthority('notice:del')")
    @Transactional
    public Result deleteNotice(@RequestBody List<Notice> noticeList) {
        Result result = iNoticeService.deleteNotice(noticeList);
        return result;
    }
}
