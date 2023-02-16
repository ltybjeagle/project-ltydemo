package com.sunny.maven.core.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: 公共配置类
 * @create: 2023-02-15 18:27
 */
@Configuration
@ComponentScan(value = {"com.sunny.maven.core.common.spring"})
public class CommonConfig {
}
