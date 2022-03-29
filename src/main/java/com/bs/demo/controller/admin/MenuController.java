package com.bs.demo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.demo.annotation.OperationLog;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.dto.MenuDto;
import com.bs.demo.entity.Menu;
import com.bs.demo.entity.Role;
import com.bs.demo.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/menu")
@Api(tags = "菜单管理")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;


    @ApiOperation("菜单懒加载")
    @PostMapping(value = "/lazy")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('menu:list')")
    public Result lazyMenu(@RequestBody Menu menu){
        List<Menu> menuList= iMenuService.lazyMenu(menu.getMenuId());
        return Result.success().message("加载菜单成功").data(menuList);
    }


    @PostMapping("/parent")
    @ResponseBody
    @ApiOperation(value = "上级菜单")
    @PreAuthorize("hasAnyAuthority('menu:list')")
    public Result getMenuParent(@RequestBody Menu menu) {
        if (menu.getParentId() == 0){
            Menu menu1 = new Menu();
            menu1.setMenuId(0);
            menu1.setMenuName("所有目录");
            return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(menu1);
        }else {
            return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iMenuService.getById(menu.getParentId()));
        }
    }

    @OperationLog("添加菜单")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加菜单")
    @PreAuthorize("hasAnyAuthority('menu:add')")
    @Transactional
    public Result userAdd(@RequestBody @Valid Menu menu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iMenuService.addMenu(menu);
        return result;
    }

    @OperationLog("修改菜单")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改菜单")
    @PreAuthorize("hasAnyAuthority('menu:edit')")
    @Transactional
    public Result userEdit(@RequestBody @Valid Menu menu,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iMenuService.updateRole(menu);
        return result;
    }

    @OperationLog("删除菜单")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除菜单")
    @PreAuthorize("hasAnyAuthority('menu:del')")
    @Transactional
    public Result deleteRole(@RequestBody List<Menu> menuList) {
        Result result = iMenuService.deleteMenu(menuList);
        return result;
    }

    //@PostMapping("/list")
    //@ResponseBody
    //@ApiOperation(value = "菜单列表")
    //@PreAuthorize("hasAnyAuthority('menu:list')")
    //public Result getMenuAll(int currentPage, int pageSize) {
    //    QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
    //    Page<Menu> page = new Page<>(currentPage, pageSize);
    //    return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iMenuService.page(page, queryWrapper));
    //}
}