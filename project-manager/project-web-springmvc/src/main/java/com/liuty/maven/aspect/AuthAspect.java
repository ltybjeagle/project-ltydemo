package com.liuty.maven.aspect;

import com.liuty.maven.annotation.Auth;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 校验用户
 */
@Aspect
@Component
public class AuthAspect {

    @Pointcut(value = "@annotation(com.liuty.maven.annotation.Auth)")
    public void controllerAuth() {}

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("controllerAuth()")
    public void controllerBefore(JoinPoint joinPoint) {

    }

    /**
     * 环绕通知
     * @param pj
     * @param auth
     * @return
     */
    @Around("@annotation(auth)")
    public Object controllerAround(ProceedingJoinPoint pj, Auth auth) {
        // 校验逻辑
        return "";
    }
}
