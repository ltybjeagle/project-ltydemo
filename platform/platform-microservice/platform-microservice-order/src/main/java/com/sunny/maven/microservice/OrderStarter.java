package com.sunny.maven.microservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author SUNNY
 * @description: 订单服务启动类
 * JVM日志参数：
 * -Xlog:gc*=error,heap*=debug
 * -Xlog:gc*=trace:gc-trace.log -Xlog:async -XX:AsyncLogBufferSize=102400
 * @create: 2023-03-23 12:12
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(value = {"com.sunny.maven.microservice.order.mapper"})
@EnableDiscoveryClient
@EnableFeignClients
public class OrderStarter {
    public static void main(String[] args) {
        SpringApplication.run(OrderStarter.class, args);
    }
}
