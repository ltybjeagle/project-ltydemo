package com.sunny.maven.cache.aspect;

import com.sunny.maven.cache.annotation.AccessLimiterAop;
import com.sunny.maven.cache.service.redis.AccessLimiter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author SUNNY
 * @description: 限流拦截器
 * @create: 2022-03-01 23:02
 */
@Aspect
@Component
public class AccessLimiterAspect {
    private static final Logger logger = LoggerFactory.getLogger(AccessLimiterAspect.class);
    private AccessLimiter accessLimiter;

    @Pointcut("@annotation(com.sunny.maven.cache.annotation.AccessLimiterAop)")
    public void cut(){
        logger.info("cut");
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint){
        // 获取方法签名，作为methodkey
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AccessLimiterAop annotation = method.getAnnotation(AccessLimiterAop.class);

        if (annotation == null) {
            return;
        }
        String key = annotation.methodKey();
        Integer limit = annotation.limit();
        // 如果没有设置methodKey，就自动添加一个
        if (StringUtils.isEmpty(key)) {
            Class[] type = method.getParameterTypes();
            key = method.getName();
            if (type != null){
                String paramTypes = Arrays.stream(type)
                        .map(Class::getName)
                        .collect(Collectors.joining(","));
                key += "#" + paramTypes;
            }
        }

        // 调用redis
        accessLimiter.limitAccess(key, limit);
    }

    @Autowired
    public AccessLimiterAspect(AccessLimiter accessLimiter) {
        this.accessLimiter = accessLimiter;
    }
}
