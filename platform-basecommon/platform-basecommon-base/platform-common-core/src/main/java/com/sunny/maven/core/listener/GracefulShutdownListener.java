package com.sunny.maven.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author SUNNY
 * @description: SpringBoot 自定义服务关闭监听
 * @create: 2022-10-18 16:49
 */
@Slf4j
public class GracefulShutdownListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 处理服务关闭资源处理逻辑
        log.info("处理服务关闭资源处理逻辑");
    }
}
