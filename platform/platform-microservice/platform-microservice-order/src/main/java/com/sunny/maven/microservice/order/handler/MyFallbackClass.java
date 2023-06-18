package com.sunny.maven.microservice.order.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/6/8 16:27
 */
@Slf4j
public class MyFallbackClass {
    public static String fallback(Throwable e) {
        log.error("异常了:{}", e.getMessage());
        return "异常了";
    }
}
