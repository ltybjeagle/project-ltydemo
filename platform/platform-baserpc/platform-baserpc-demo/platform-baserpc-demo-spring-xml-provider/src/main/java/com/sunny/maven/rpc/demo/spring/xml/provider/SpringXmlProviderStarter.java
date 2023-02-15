package com.sunny.maven.rpc.demo.spring.xml.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author SUNNY
 * @description: 服务提供者启动类
 * @create: 2023-02-14 16:26
 */
public class SpringXmlProviderStarter {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server-spring.xml");
    }
}
