package com.sunny.maven.core.annotation.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SUNNY
 * @description: 接口版本控制
 * @create: 2023/6/28 22:56
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    /**
     * 默认接口版本号1开始，这里我只做了两级，多级可在正则进行控制
     * @return
     */
    int value() default 1;
}
