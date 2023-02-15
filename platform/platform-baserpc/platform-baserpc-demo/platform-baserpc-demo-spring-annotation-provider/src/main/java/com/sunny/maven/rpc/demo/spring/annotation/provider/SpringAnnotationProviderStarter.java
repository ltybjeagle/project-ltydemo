package com.sunny.maven.rpc.demo.spring.annotation.provider;

import com.sunny.maven.rpc.demo.spring.annotation.provider.config.SpringAnnotationProviderConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author SUNNY
 * @description: 基于Spring注解的服务提供者启动类
 * @create: 2023-02-14 17:52
 */
public class SpringAnnotationProviderStarter {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(SpringAnnotationProviderConfig.class);
    }
}
