package com.bs.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.demo.common.Result;
import com.bs.demo.entity.Logs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public interface ILogsService extends IService<Logs> {



    @Async
    void save(String userName, String browser, String ip, ProceedingJoinPoint joinPoint, Logs logs);



    Result deleteLogs(List<Logs> logsList);
}
