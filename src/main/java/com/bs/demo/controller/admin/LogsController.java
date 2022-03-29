package com.bs.demo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.demo.annotation.OperationLog;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;
import com.bs.demo.entity.Logs;
import com.bs.demo.entity.Role;
import com.bs.demo.service.ILogsService;
import com.bs.demo.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/logs")
@Api(tags = "日志管理")
public class LogsController {

    @Autowired
    private ILogsService iLogsService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "日志列表")
    @PreAuthorize("hasAnyAuthority('log:monitor')")
    public Result logList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Logs> queryWrapper = new QueryWrapper<>();
        Page<Logs> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("description", searchOption.getSearchText()).or()
                .like("user_name", searchOption.getSearchText()).or()
                .like("request_ip", searchOption.getSearchText()).or()
                .like("params", searchOption.getSearchText()).or()
                .like("address", searchOption.getSearchText()).or()
                .like("browser", searchOption.getSearchText()).orderByDesc("create_time");
        Page<Logs> logList = iLogsService.page(page, queryWrapper);
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(logList);
    }

    @PostMapping("/personal")
    @ResponseBody
    @ApiOperation(value = "个人日志")
    public Result logPersonalList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Logs> queryWrapper = new QueryWrapper<>();
        Page<Logs> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.eq("user_name", SecurityUtils.getCurrentUsername()).orderByDesc("create_time");
        Page<Logs> logList = iLogsService.page(page, queryWrapper);
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(logList);
    }

    @OperationLog("删除日志")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除日志")
    @PreAuthorize("hasAnyAuthority('logs:del')")
    @Transactional
    public Result deleteRole(@RequestBody List<Logs> logsList) {
        Result result = iLogsService.deleteLogs(logsList);
        return result;
    }
}
