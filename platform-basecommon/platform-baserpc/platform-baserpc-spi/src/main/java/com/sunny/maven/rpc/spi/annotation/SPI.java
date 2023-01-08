package com.sunny.maven.rpc.spi.annotation;

import java.lang.annotation.*;

/**
 * @author SUNNY
 * @description: @SPI
 * @create: 2023-01-06 10:58
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {
    /**
     * 默认的实现方式
     * @return
     */
    String value() default "";
}
