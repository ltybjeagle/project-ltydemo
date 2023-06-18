package com.sunny.maven.middle.authentication.jwt.password.annotation;

import java.lang.annotation.*;

/**
 * @author SUNNY
 * @description: @SPIPasswordCodec
 * @create: 2023-04-03 23:10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPIPasswordCodec {
    String value() default "";
}
