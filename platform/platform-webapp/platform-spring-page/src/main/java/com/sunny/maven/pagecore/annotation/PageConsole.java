package com.sunny.maven.pagecore.annotation;

import java.lang.annotation.*;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/24 15:28
 * @description：Page请求注解
 * @modified By：
 * @version: 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageConsole {
    String page() default "";

    String url();

    String template() default "";

    String jslib() default "";

    String csslib() default "";

    String objlib() default "";

    String appid();

    String config() default "";

    String supconsole() default "";

    PageComponent[] component() default {};

    PageTemplate pageTempltate() default @PageTemplate();

    String region() default "";
}
