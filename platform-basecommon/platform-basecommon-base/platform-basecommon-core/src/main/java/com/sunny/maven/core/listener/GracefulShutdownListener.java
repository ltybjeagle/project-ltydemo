package com.sunny.maven.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author SUNNY
 * @description: SpringBoot 自定义服务关闭监听
 * @create: 2021-07-29 11:23
 */
public class GracefulShutdownListener implements ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GracefulShutdownListener.class);
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        // 处理服务关闭资源处理逻辑
        logger.info("处理服务关闭资源处理逻辑");
    }
}
