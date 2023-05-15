package com.sunny.maven.springframework.context;

import com.sunny.maven.springframework.beans.BeansException;

/**
 * @author SUNNY
 * @description: SPI 接口配置应用上下文 SPI interface to be implemented by most if not all application contexts.
 * Provides facilities to configure an application context in addition
 * to the application context client methods in the
 * {@link com.sunny.maven.springframework.context.ApplicationContext} interface.
 * @create: 2023-03-26 22:17
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();
}
