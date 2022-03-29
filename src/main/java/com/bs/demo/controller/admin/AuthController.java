package com.bs.demo.controller.admin;

import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.dto.LoginUserDto;
import com.bs.demo.entity.User;
import com.bs.demo.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/auth")
@Api(tags = "信息验证")
public class AuthController {

    @Autowired
    private IMenuService iMenuService;

    //@AuthenticationPrincipal
    //UserDetails userDetails
    @PostMapping(value = "/user")
    @ResponseBody
    @ApiOperation(value = "获取已登录用户信息")
    public Result getLoginUser() {
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(loginUserDto.getUser()) ;
    }



    @PostMapping(value = "/menu")
    @ResponseBody
    @ApiOperation(value = "通过用户id获取菜单")
    public Result getMenu() {
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = loginUserDto.getUser().getUserId();
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(iMenuService.getMenu(userId)) ;
    }

    @PostMapping(value = "/permission")
    @ResponseBody
    @ApiOperation(value = "通过用户id查询权限")
    public Result getPermission() {
        LoginUserDto loginUserDto = (LoginUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(loginUserDto.getRoles()) ;
    }
}
