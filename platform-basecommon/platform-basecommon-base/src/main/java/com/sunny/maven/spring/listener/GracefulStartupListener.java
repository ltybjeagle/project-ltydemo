package com.sunny.maven.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: SpringBoot 自定义服务启动监听
 * @create: 2021-07-29 11:28
 */
@Slf4j
@Component
public class GracefulStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 处理服务启动初始化逻辑
        log.info("处理服务启动初始化逻辑");
    }
}
