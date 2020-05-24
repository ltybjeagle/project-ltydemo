package com.sunny.maven.pagecore.annotation;

import java.lang.annotation.*;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/24 15:33
 * @description：Page模板注解
 * @modified By：
 * @version: 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageTemplate {
    String funcShow() default "";

    String templateService() default "";
}
