package com.sunny.maven.rpc.demo.spring.boot.consumer;

import com.sunny.maven.rpc.demo.spring.boot.consumer.service.ConsumerDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author SUNNY
 * @description: 服务消费者基于SpringBoot的启动类
 * @create: 2023-02-17 15:21
 */
@Slf4j
@SpringBootApplication
@ComponentScan(value = {"com.sunny.maven.rpc"})
public class SpringBootConsumerDemoStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootConsumerDemoStarter.class, args);
        ConsumerDemoService consumerDemoService = context.getBean(ConsumerDemoService.class);
        String result = consumerDemoService.hello("SUNNY");
        log.info("返回的结果数据===>>> {}", result);
    }
}
