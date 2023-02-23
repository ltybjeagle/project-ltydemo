package com.sunny.maven.rpc.demo.docker.consumer;

import com.sunny.maven.rpc.demo.docker.consumer.service.ConsumerDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author SUNNY
 * @description: 基于Docker接入服务消费者启动类
 * @create: 2023-02-22 17:23
 */
@Slf4j
@SpringBootApplication
@ComponentScan(value = {"com.sunny.maven.rpc"})
public class DockerConsumerDemoStarter {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DockerConsumerDemoStarter.class, args);
        ConsumerDemoService consumerDemoService = context.getBean(ConsumerDemoService.class);
        String result = consumerDemoService.hello("SUNNY");
        log.info("返回的结果数据===>>> {}", result);
    }
}
