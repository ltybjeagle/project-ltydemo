package com.sunny.maven.core.fluxclient.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SUNNY
 * @description: 服务器相关信息
 * @create: 2022-09-30 10:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiServer {
    String value() default "";
}
