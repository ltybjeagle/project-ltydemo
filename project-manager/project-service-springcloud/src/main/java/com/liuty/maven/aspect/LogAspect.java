package com.liuty.maven.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务日志切面
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 拦截此包下的所有请求
     */
    @Pointcut("execution(* com.liuty.maven.service.*.*(..))")
    public void serviceLog() {}

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("serviceLog()")
    public void serviceBefore(JoinPoint joinPoint) {
        logger.info("======================》服务调用");
        // 函数名称
        String name = joinPoint.getSignature().getName();
        // 类路径
        String pkg = joinPoint.getSignature().getDeclaringTypeName();
        // 函数参数
        Object[] args = joinPoint.getArgs();
        List<String> argNames = Arrays.stream(args).map(arg -> arg.getClass().getName()).collect(Collectors.toList());
        logger.info("函数：{} :: {} ( {} )", pkg, name, argNames.toString());
    }

    /**
     * 方法调用结束通知
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "serviceLog()")
    public void serviceAfterReturning(Object object) {

    }

    /**
     * 操作异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(throwing = "e", pointcut = "serviceLog()")
    public void serviceAfterThrowing(JoinPoint joinPoint, Exception e) {

    }

    /**
     * 后置通知
     */
    @After("serviceLog()")
    public void serviceAfter() {

    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint
     * @return
     */
    @Around("serviceLog()")
    public Object serviceAround(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            long startTime = System.currentTimeMillis();
            Object obj = proceedingJoinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("服务执行耗时：{} ms", (endTime - startTime));
            return obj;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
