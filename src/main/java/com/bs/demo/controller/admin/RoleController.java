package com.bs.demo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;

import com.bs.demo.service.IRoleService;
import com.bs.demo.entity.Role;


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
 * @author gf
 * @createTime 2020/7/10
 */
@Controller
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    @PostMapping("/all")
    @ResponseBody
    @ApiOperation(value = "获取可用角色")
    @PreAuthorize("hasAnyAuthority('role:list','user:add','user:edit')")
    public Result roleAll() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iRoleService.list(queryWrapper));
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "角色列表")
    @PreAuthorize("hasAnyAuthority('user:list')")
    public Result roleList(@RequestBody SearchOption searchOption) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        Page<Role> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("role_name", searchOption.getSearchText()).or()
                .like("description", searchOption.getSearchText())
                .orderByDesc("create_time");
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iRoleService.page(page, queryWrapper));
    }

    @OperationLog("添加角色")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加角色")
    @PreAuthorize("hasAnyAuthority('role:add')")
    @Transactional
    public Result userAdd(@RequestBody @Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iRoleService.addRole(role);
        return result;
    }

    @OperationLog("修改菜单")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改角色")
    @PreAuthorize("hasAnyAuthority('role:edit')")
    @Transactional
    public Result userEdit(@RequestBody @Valid Role role,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iRoleService.updateRole(role);
        return result;
    }

    @OperationLog("删除菜单")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除角色")
    @PreAuthorize("hasAnyAuthority('role:del')")
    @Transactional
    public Result deleteRole(@RequestBody List<Role> roleList) {
        Result result = iRoleService.deleteRole(roleList);
        return result;
    }
}
