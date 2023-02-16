package com.sunny.maven.rpc.demo.spring.annotation.consumer;

import com.sunny.maven.rpc.demo.spring.annotation.consumer.config.SpringAnnotationConsumerConfig;
import com.sunny.maven.rpc.demo.spring.annotation.consumer.service.ConsumerDemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author SUNNY
 * @description: 基于Spring注解的消费者测试类
 * @create: 2023-02-15 14:26
 */
@Slf4j
public class SpringAnnotationConsumerTest {

    @Test
    public void testInterfaceRpc() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringAnnotationConsumerConfig.class);
        ConsumerDemoService consumerDemoService = context.getBean(ConsumerDemoService.class);
        String result = consumerDemoService.hello("SUNNY");
        log.info("返回的结果数据===>>> {}", result);
    }
}
