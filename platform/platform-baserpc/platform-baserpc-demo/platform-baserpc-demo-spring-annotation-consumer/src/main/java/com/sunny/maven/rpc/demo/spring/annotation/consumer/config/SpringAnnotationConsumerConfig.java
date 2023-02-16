package com.sunny.maven.rpc.demo.spring.annotation.consumer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: 服务消费者注解配置类
 * @create: 2023-02-15 14:29
 */
@Configuration
@ComponentScan(value = {"com.sunny.maven.rpc.*"})
public class SpringAnnotationConsumerConfig {
}
