package com.bs.demo.security;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.bs.demo.common.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 * @author gf
 * @createTime 2021/12/28
 */
@Component
public class VerifyCodeFilter extends OncePerRequestFilter {


    private String defaultFilterProcessUrl = "/login";
    private String method = "POST";

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (method.equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            String key = request.getParameter("key");
            String captcha = request.getParameter("captcha");
            String redisCode = stringRedisTemplate.opsForValue().get(key);

            // 登录请求校验验证码，非登录请求不用校验
//            HttpSession session = request.getSession();
//            String requestCaptcha = request.getParameter("captcha");
//            //验证码的信息存放在seesion种，具体看EasyCaptcha官方解释
//            String genCaptcha = (String) request.getSession().getAttribute("captcha");
            response.setContentType("application/json;charset=UTF-8");
            if (StrUtil.isEmpty(captcha)){
                //删除缓存里的验证码信息
//                session.removeAttribute("captcha");
                response.getWriter().write(JSON.toJSONString(Result.error().message("验证码不能为空!")));
                return;
            }

            if (!StrUtil.equalsIgnoreCase(captcha,redisCode)){
//                session.removeAttribute("captcha");
                stringRedisTemplate.delete(key);
                response.getWriter().write(JSON.toJSONString(Result.error().message("验证码错误!")));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
