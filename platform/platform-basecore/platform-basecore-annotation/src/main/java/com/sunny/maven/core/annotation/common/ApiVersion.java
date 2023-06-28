package com.sunny.maven.core.annotation.common;

/**
 * @author SUNNY
 * @description: 接口版本控制
 * @create: 2023/6/28 22:56
 */
public @interface ApiVersion {
    /**
     * 默认接口版本号1.0开始，这里我只做了两级，多级可在正则进行控制
     * @return
     */
    String value() default "1.0";
}
