package com.sunny.maven.listener;

import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/1 20:05
 * @description：服务状态监听器
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Component
public class EurekaStateChangeListener {
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        log.info("{}\t{}服务下线", event.getServerId(), event.getAppName());
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        log.info("{}进行注册", instanceInfo.getAppName());
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        log.info("{}\t{}服务进行续约", event.getServerId(), event.getAppName());
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        log.info("注册中心 启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        log.info("Eureka Server 启动");
    }
}
