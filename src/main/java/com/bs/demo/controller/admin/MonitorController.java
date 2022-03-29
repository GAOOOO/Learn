package com.bs.demo.controller.admin;

import com.bs.demo.common.Result;
import com.bs.demo.service.IMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = "系统监控")
@RequestMapping("/monitor")
public class MonitorController {
    private final IMonitorService iMonitorService;

    @PostMapping("/server")
    @ApiOperation("服务监控")
    @PreAuthorize("hasAnyAuthority('server:monitor')")
    public Result queryMonitor(HttpServletRequest request){
        return Result.success().message("获取成功").data(iMonitorService.getServers(request));
    }
}
