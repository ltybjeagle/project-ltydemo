package com.sunny.maven.middle.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: AOP实现日志监控
 * @create: 2023/7/21 17:49
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(* com.sunny.maven.user..*.*(..))")
    public void pcl() {}

    @Before(value = "pcl()")
    public void before(JoinPoint jp) {
        String name = jp.getSignature().getName();
        log.info("{}方法开始执行...", name);
    }

    @After(value = "pcl()")
    public void after(JoinPoint jp) {
        String name = jp.getSignature().getName();
        log.info("{}方法执行结束...", name);
    }

    @AfterReturning(value = "pcl()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        String name = jp.getSignature().getName();
        log.info("{}方法返回值为:{}", name, result);
    }

    @AfterThrowing(value = "pcl()", throwing = "e")
    public void afterThrowing(JoinPoint jp, Exception e) {
        String name = jp.getSignature().getName();
        log.info("{}方法抛异常了，异常是:{}", name, e.getMessage());
    }

    @Around(value = "pcl()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }
}
