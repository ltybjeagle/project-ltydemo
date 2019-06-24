package com.liuty.maven.command;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @描述: spring事件监听
 * 两种初始化方式：
 * 第一种：
 * 配置：\resources\META-INF\spring.factories
 *  org.springframework.context.ApplicationEvent=\
 *    com.liuty.maven.command.DemoApplicationListener
 * 第二种：
 * SpringApplication.addListeners();
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/24
 */
public class DemoApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
