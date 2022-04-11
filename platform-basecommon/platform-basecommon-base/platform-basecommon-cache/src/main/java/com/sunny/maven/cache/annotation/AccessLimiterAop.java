package com.sunny.maven.cache.annotation;

import java.lang.annotation.*;

/**
 * @author SUNNY
 * @description: 限流注解
 * @create: 2022-01-25 23:46
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimiterAop {
    int limit();

    String methodKey() default "";
}
