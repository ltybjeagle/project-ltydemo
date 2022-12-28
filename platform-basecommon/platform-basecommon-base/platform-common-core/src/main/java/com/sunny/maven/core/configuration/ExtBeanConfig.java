package com.sunny.maven.core.configuration;

import com.sunny.maven.core.common.context.CommonApplicationContext;
import com.sunny.maven.core.extend.*;
import com.sunny.maven.core.listener.GracefulShutdownListener;
import com.sunny.maven.core.listener.GracefulStartupListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: 扩展配置
 * @create: 2022-10-17 23:38
 */
@Configuration
public class ExtBeanConfig {
    /**
     * 注册上下文
     * @return CommonApplicationContext
     */
    @Bean
    public CommonApplicationContext commonApplicationContext() {
        return new CommonApplicationContext();
    }
    /**
     * 服务启动事件监听
     * @return GracefulStartupListener
     */
    @Bean
    public GracefulStartupListener gracefulStartupListener() {
        return new GracefulStartupListener();
    }
    /**
     * 服务关闭事件监听
     * @return GracefulShutdownListener
     */
    @Bean
    public GracefulShutdownListener gracefulShutdownListener() {
        return new GracefulShutdownListener();
    }
    @Bean
    public ExtBeanDefinitionRegistryPostProcessor extBeanDefinitionRegistryPostProcessor() {
        return new ExtBeanDefinitionRegistryPostProcessor();
    }
    @Bean
    public ExtBeanFactoryPostProcessor extBeanFactoryPostProcessor() {
        return new ExtBeanFactoryPostProcessor();
    }
    @Bean
    public ExtInstantiationAwareBeanPostProcessor extInstantiationAwareBeanPostProcessor() {
        return new ExtInstantiationAwareBeanPostProcessor();
    }
    @Bean
    public ExtSmartInstantiationAwareBeanPostProcessor extSmartInstantiationAwareBeanPostProcessor() {
        return new ExtSmartInstantiationAwareBeanPostProcessor();
    }
    @Bean
    public ExtBeanFactoryAware extBeanFactoryAware() {
        return new ExtBeanFactoryAware();
    }
    @Bean
    public ExtBeanNameAware extBeanNameAware() {
        return new ExtBeanNameAware();
    }
    @Bean
    public ExtInitializingBean extInitializingBean() {
        return new ExtInitializingBean();
    }
    @Bean
    public ExtFactoryBean extFactoryBean() {
        return new ExtFactoryBean();
    }
    @Bean
    public ExtSmartInitializingSingleton extSmartInitializingSingleton() {
        return new ExtSmartInitializingSingleton();
    }
    @Bean
    public ExtCommandLineRunner extCommandLineRunner() {
        return new ExtCommandLineRunner();
    }
    @Bean
    public ExtDisposableBean extDisposableBean() {
        return new ExtDisposableBean();
    }
}
