package com.bs.demo.security;

import com.alibaba.fastjson.JSON;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录处理
 * @author gf
 * @createTime 2021/12/28
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
//        response.getWriter().println(JSON.toJSONString(Result.error().code(ResultCode.NOT_LOGIN).message("尚未登录，或者登录过期！   " + authException.getMessage())));
        response.getWriter().println(JSON.toJSONString(Result.error().code(ResultCode.NOT_LOGIN).message("尚未登录，或者登录过期！")));
        response.getWriter().flush();
    }
}
