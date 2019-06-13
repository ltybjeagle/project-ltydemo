package com.liuty.maven.annotation;

import java.lang.annotation.*;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/5/30
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface CacheDevPf {
}
