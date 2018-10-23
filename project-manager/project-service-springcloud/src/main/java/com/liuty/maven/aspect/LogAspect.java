package com.liuty.maven.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 服务日志切面
 */
@Aspect
@Component
public class LogAspect {

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
        // 函数名称
        joinPoint.getSignature().getName();
        // 类路径
        joinPoint.getSignature().getDeclaringTypeName();
        // 函数参数
        joinPoint.getArgs();
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
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
