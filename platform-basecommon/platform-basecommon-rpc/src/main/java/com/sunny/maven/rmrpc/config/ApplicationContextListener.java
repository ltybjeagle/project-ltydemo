package com.sunny.maven.rmrpc.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/24 20:56
 * @description：Spring容器事件监听
 * @modified By：
 * @version: 1.0.0
 */
@Component
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // root application context
        if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
            Map<String, ServerBean> baseServices =
                    contextRefreshedEvent.getApplicationContext().getBeansOfType(ServerBean.class);
            if (!baseServices.isEmpty()) {
                // 启动RPC服务
            }
        }
    }
}
