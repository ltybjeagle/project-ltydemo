package com.sunny.maven.core.annotation.common;

import java.lang.annotation.*;

/**
 * @author SUNNY
 * @description: 操作日志(服务接口层使用).
 * @create: 2023/7/21 18:40
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 是否保存请求的参数
     * @return the boolean
     */
    boolean isSaveRequestData() default false;

    /**
     * 是否保存响应的结果
     * @return the boolean
     */
    boolean isSaveResponseData() default false;
}
