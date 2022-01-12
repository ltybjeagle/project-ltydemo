package com.sunny.maven.listener;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 服务状态监控
 * @create: 2021-11-30 13:46
 */
@Component
public class EurekaStateChangeListener {
    private static final Logger logger = LoggerFactory.getLogger(EurekaStateChangeListener.class);

    /**
     * 服务下线监听
     * @param event 下线事件
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        logger.info("{} \t {} 服务下线 ", event.getServerId(), event.getAppName());
    }

    /**
     * 服务注册监听
     * @param event 注册事件
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        logger.info("{} 进行注册 ", instanceInfo.getAppName());
    }

    /**
     * 服务续约监听
     * @param event 续约事件
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        logger.info("{} \t {} 服务进行续约 ", event.getServerId(), event.getAppName());
    }

    /**
     * 注册中心启动监听
     * @param event 注册中心启动事件
     */
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        logger.info(" 注册中心启动 ");
    }

    /**
     * 服务启动监听
     * @param event 服务启动事件
     */
    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        logger.info(" Eureka Server启动 ");
    }
}
