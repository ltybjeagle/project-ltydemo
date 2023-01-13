package com.sunny.maven.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author SUNNY
 * @description: SpringBoot 自定义服务启动监听
 * @create: 2022-10-18 16:47
 */
@Slf4j
public class GracefulStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 处理服务启动初始化逻辑
        log.info("处理服务启动初始化逻辑");
    }
}
