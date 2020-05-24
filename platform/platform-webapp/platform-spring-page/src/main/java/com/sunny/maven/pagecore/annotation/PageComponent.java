package com.sunny.maven.pagecore.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/24 15:31
 * @description：Page组件注解
 * @modified By：
 * @version: 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PageComponent {
    @Deprecated
    String componentid() default "";

    String viewid() default "";

    String jsLib() default "";

    String serviceid() default "";

    String jsObjectName() default "";

    String serviceclass() default "";

    String servicebean() default "";

    String config() default "";

    String id() default "";

    String region() default "";

    String appid() default "";

    String type() default "";
}
