package com.sunny.maven.rmrpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/9 19:44
 * @description：rpc服务类注解
 * @modified By：
 * @version: 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RmRpcService {
    /**
     * 实现接口
     * @return
     */
    Class<?> value();
}
