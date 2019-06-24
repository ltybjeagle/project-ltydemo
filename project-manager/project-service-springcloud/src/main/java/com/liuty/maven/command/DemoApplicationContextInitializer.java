package com.liuty.maven.command;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @描述: 扩展ConfigurableApplicationContext的处理
 * 两种初始化方式：
 * 第一种：
 * 配置：\resources\META-INF\spring.factories
 *  org.springframework.context.ApplicationContextInitializer=\
 *    com.liuty.maven.command.DemoApplicationContextInitializer
 * 第二种：
 * SpringApplication.addInitializers();
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/24
 */
public class DemoApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

    }
}
