package com.sunny.maven.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SUNNY
 * @description: 服务提供者注解
 * @create: 2022-12-20 15:21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {
    /**
     * 接口的Class
     */
    Class<?> interfaceClass() default void.class;
    /**
     * 接口的ClassName
     */
    String interfaceClassName() default "";
    /**
     * 版本号
     */
    String version() default "1.0.0";
    /**
     * 服务分组，默认为空
     */
    String group() default "";
    /**
     * 权重
     */
    int weight() default 0;
}
