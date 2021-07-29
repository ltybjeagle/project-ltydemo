package com.sunny.maven.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: SpringBoot 自定义服务关闭监听
 * @create: 2021-07-29 11:23
 */
@Slf4j
@Component
public class GracefulShutdownListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        // 处理服务关闭资源处理逻辑
        log.info("处理服务关闭资源处理逻辑");
    }
}
