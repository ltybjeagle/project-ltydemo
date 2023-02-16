package com.sunny.maven.rpc.demo.spring.xml.consumer;

import com.sunny.maven.rpc.consumer.RpcClient;
import com.sunny.maven.rpc.demo.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SUNNY
 * @description: 测试服务消费者整合Spring XML
 * @create: 2023-02-15 13:53
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:client-spring.xml")
public class SpringXmlConsumerTest {

    @Autowired
    private RpcClient rpcClient;

    @Test
    public void testInterfaceRpc() throws Exception {
        DemoService demoService = rpcClient.create(DemoService.class);
        String result = demoService.hello("SUNNY");
        log.info("返回的结果数据===>>> {}", result);
        while (true) {
            Thread.sleep(1000);
        }
    }
}
