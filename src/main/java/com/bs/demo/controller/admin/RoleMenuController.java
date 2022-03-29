package com.bs.demo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.demo.annotation.OperationLog;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleMenu;
import com.bs.demo.entity.User;
import com.bs.demo.service.IRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gf
 * @createTime 2020/7/10
 */
@Controller
@RequestMapping("/roleMenu")
@Api(tags = "角色权限管理")
public class RoleMenuController {

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "通过角色id获取菜单")
    @PreAuthorize("hasAnyAuthority('role:list')")
    public Result roleMenuList(@RequestBody Role role) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",role.getRoleId());
        List<RoleMenu> roleMenuList = iRoleMenuService.list(queryWrapper);

        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList()));
    }

    @OperationLog("更改角色权限")
    @PostMapping("/change")
    @ResponseBody
    @ApiOperation(value = "更改角色权限")
    @PreAuthorize("hasAnyAuthority('role:menu:change')")
    public Result roleMenuChange(@RequestBody RoleMenu roleMenu) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleMenu.getRoleId()).eq("menu_id",roleMenu.getMenuId());
        List<RoleMenu> roleMenuList = iRoleMenuService.list(queryWrapper);
        if (roleMenuList.size()>0){
            iRoleMenuService.remove(queryWrapper);
        }else {
            iRoleMenuService.save(roleMenu);
        }
        return Result.success().code(ResultCode.SUCCESS).message("修改成功");
    }

}
