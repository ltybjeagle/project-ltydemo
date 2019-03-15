package com.liuty.maven.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Spring组件上下文配置类
 */
@Configuration
@ComponentScan(basePackages = {
        "com.liuty.maven.aspect",
        "com.liuty.maven.handler",
        "com.liuty.maven.service",
        "com.liuty.maven.dao"
})
@Order(3)
public class RootConfig {
    private static final Logger logger = LoggerFactory.getLogger(RootConfig.class);
    public RootConfig() {
        logger.info("RootConfig容器启动......");
    }
}
