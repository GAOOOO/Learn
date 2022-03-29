package com.bs.demo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.demo.annotation.OperationLog;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.common.SearchOption;
import com.bs.demo.dto.LoginUserDto;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleUser;
import com.bs.demo.entity.User;
import com.bs.demo.entity.vo.RoleUserVo;
import com.bs.demo.service.IRoleService;
import com.bs.demo.service.IRoleUserService;
import com.bs.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private IRoleService iRoleService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAnyAuthority('user:list')")
    public Result userList(@RequestBody SearchOption searchOption) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> page = new Page<>(searchOption.getCurrentPage(), searchOption.getPageSize());
        queryWrapper.like("user_name", searchOption.getSearchText()).or()
                .like("nick_name", searchOption.getSearchText()).or()
                .like("phone", searchOption.getSearchText()).or()
                .like("email", searchOption.getSearchText()).or()
                .like("create_user", searchOption.getSearchText()).or()
                .like("update_user", searchOption.getSearchText())
                .orderByDesc("create_time");
        Page<User> userList = iUserService.page(page, queryWrapper);
        userList.getRecords().forEach(user -> {
            Role userRole = iRoleUserService.getUserRole(user.getUserId());
            user.setRoleId(userRole.getRoleId());
            user.setRoleName(userRole.getRoleName());
            user.setDescription(userRole.getDescription());
        });
        return Result.success().code(ResultCode.SUCCESS).message("查询成功").data(userList);
    }

    @OperationLog("添加用户")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加用户")
    @PreAuthorize("hasAnyAuthority('user:add')")
    @Transactional
    public Result userAdd(@RequestBody @Valid RoleUserVo roleUserVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iUserService.addUser(roleUserVo);
        return result;
    }

    @OperationLog("修改用户")
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAnyAuthority('user:edit')")
    @Transactional
    public Result userEdit(@RequestBody @Valid RoleUserVo tbUser,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Result result = iUserService.updateUser(tbUser);
        return result;
    }


    @OperationLog("删除用户")
    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("删除用户")
    @PreAuthorize("hasAnyAuthority('user:del')")
    @Transactional
    public Result deleteUser(@RequestBody List<User> userList) {
        Result result = iUserService.deleteUser(userList);
        return result;

    }
}
