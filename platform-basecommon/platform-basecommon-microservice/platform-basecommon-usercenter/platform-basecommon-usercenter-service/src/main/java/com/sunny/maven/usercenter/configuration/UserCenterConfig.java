package com.sunny.maven.usercenter.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: 用户中心配置类
 * @create: 2022-02-24 10:27
 */
@Configuration
@MapperScan(basePackages = {"com.sunny.maven.usercenter.mapper"})
public class UserCenterConfig {
}
