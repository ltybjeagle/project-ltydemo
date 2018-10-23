package com.liuty.maven.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring组件上下文配置类
 */
@Configuration
@ComponentScan(basePackages = {"com.liuty.maven.aspect", "com.liuty.maven.handler"})
public class DemoRootConfig {
}
