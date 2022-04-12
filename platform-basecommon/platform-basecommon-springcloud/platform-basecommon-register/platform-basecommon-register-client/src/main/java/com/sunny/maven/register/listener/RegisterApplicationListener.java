package com.sunny.maven.register.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author SUNNY
 * @description: 实例注册事件（注册后）
 * @create: 2022-03-02 18:18
 */
public class RegisterApplicationListener implements ApplicationListener<InstanceRegisteredEvent> {
    private static final Logger logger = LoggerFactory.getLogger(RegisterApplicationListener.class);
    @Override
    public void onApplicationEvent(InstanceRegisteredEvent event) {
        logger.info("{}注册完成", event.getSource());
    }
}
