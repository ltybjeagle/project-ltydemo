package com.sunny.maven.usercenter.security.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: Oauth2Comm配置类
 * @create: 2022-02-24 10:45
 */
@Configuration
@MapperScan(basePackages = {"com.sunny.maven.usercenter.security.mapper"})
public class Oauth2CommConfig {
}
