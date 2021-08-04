package com.sunny.maven.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: SpringBoot 自定义服务启动监听
 * @create: 2021-07-29 11:28
 */
@Component
public class GracefulStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GracefulStartupListener.class);
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 处理服务启动初始化逻辑
        logger.info("处理服务启动初始化逻辑");
    }
}
