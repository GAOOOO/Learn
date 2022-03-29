package com.bs.demo.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;
import com.bs.demo.entity.Logs;
import com.bs.demo.entity.Role;
import com.bs.demo.mapper.LogsMapper;
import com.bs.demo.service.ILogsService;
import com.bs.demo.utils.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {
    @Autowired
    private LogsMapper logsMapper;

    @Override
    public void save(String userName, String browser, String ip, ProceedingJoinPoint joinPoint, Logs logs) {
        if (logs == null) {
            throw new IllegalArgumentException("Logs 不能为 null!");
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.bs.demo.annotation.OperationLog aopLog = method.getAnnotation(com.bs.demo.annotation.OperationLog.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        // 描述
        logs.setDescription(aopLog.value());
        logs.setRequestIp(ip);
        logs.setAddress(IpUtils.getHttpCityInfo(logs.getRequestIp()));
        logs.setMethod(methodName);
        logs.setUserName(userName);
        logs.setParams(getParameter(method, joinPoint.getArgs()));
        logs.setBrowser(browser);
        logs.setCreateTime(LocalDateTime.now());
        logsMapper.insert(logs);
    }

    @Override
    public Result deleteLogs(List<Logs> logsList) {
        //统计要删除的ID
        List<Long> ids = logsList.stream().map(Logs::getLogId).collect(Collectors.toList());
        int resultCode = logsMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>(4);
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.isEmpty()) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

}
