package com.sunny.maven.core.annotation.ratelimiter;

import java.lang.annotation.*;

/**
 * @author SUNNY
 * @description: 限流标识注解
 * @create: 2023/5/31 14:33
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPIRateLimiter {
    String value() default "";
}
