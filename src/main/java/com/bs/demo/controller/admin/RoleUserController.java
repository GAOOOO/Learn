package com.bs.demo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;
import com.bs.demo.entity.RoleUser;
import com.bs.demo.entity.User;
import com.bs.demo.service.IRoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gf
 * @createTime 2020/7/10
 */
@Controller
@RequestMapping("/roleUser")
@Api(tags = "角色用户管理")
public class RoleUserController {

    @Autowired
    private IRoleUserService iRoleUserService;

    @PostMapping("/select")
    @ResponseBody
    @ApiOperation(value = "获取用户角色")
    @PreAuthorize("hasAnyAuthority('role:list','user:list','user:edit')")
    public Result roleUser(@RequestBody RoleUser roleUser) {
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iRoleUserService.getById(roleUser.getUserId()));
    }

}
