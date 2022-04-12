package com.sunny.maven.register.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.event.InstancePreRegisteredEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author SUNNY
 * @description: 实例注册事件（注册前）
 * @create: 2022-03-02 18:00
 */
public class RegisterPreApplicationListener implements ApplicationListener<InstancePreRegisteredEvent> {
    private static final Logger logger = LoggerFactory.getLogger(RegisterPreApplicationListener.class);
    @Override
    public void onApplicationEvent(InstancePreRegisteredEvent event) {
        logger.info("{}开始注册", event.getSource());
    }
}
