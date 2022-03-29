package com.bs.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.demo.common.Result;
import com.bs.demo.entity.User;
import com.bs.demo.service.IUserService;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@Api(tags = {"私人管理"})
public class PersonalController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户注册
     * @param request
     * @param user
     * @param code
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    @ResponseBody
    public Result register(HttpServletRequest request, User user, String code) {
        User userCheck = iUserService.getOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        if (userCheck == null) {
            // 对验证码进行判断
            if (!CaptchaUtil.ver(code, request)) {
                CaptchaUtil.clear(request);  // 清除session中的验证码
                return Result.error().message("验证码错误！");
            }
            //密码加密
            String bCryptPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(bCryptPassword);
            user.setStatus(1);
//            user.setCreateTime();
            iUserService.save(user);
            return Result.success().message("注册成功！");
        } else {
            return Result.error().message("该用户已存在");
        }

    }
}
