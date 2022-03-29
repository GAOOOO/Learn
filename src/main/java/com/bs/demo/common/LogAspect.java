package com.bs.demo.common;

import com.bs.demo.entity.Logs;
import com.bs.demo.service.ILogsService;
import com.bs.demo.utils.IpUtils;
import com.bs.demo.utils.RequestHolder;
import com.bs.demo.utils.SecurityUtils;
import com.bs.demo.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    private final ILogsService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();


    public LogAspect(ILogsService logService) {
        this.logService = logService;
    }


    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.bs.demo.annotation.OperationLog)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Logs logs = new Logs("ERROR",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        logs.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), IpUtils.getBrowser(request), IpUtils.getIp(request), (ProceedingJoinPoint)joinPoint, logs);
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Logs logs = new Logs("INFO",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), IpUtils.getBrowser(request), IpUtils.getIp(request),joinPoint, logs);
        return result;
    }

    public String getUsername() {
        try {
            return SecurityUtils.getCurrentUsername();
        }catch (Exception e){
            return "";
        }
    }

}
