package com.sunny.maven.rpc.demo.consumer.hello;

import com.sunny.maven.rpc.demo.api.DemoService;

/**
 * @author SUNNY
 * @description: helloService服务实现类
 * @create: 2023-03-06 13:41
 */
public class FallbackDemoServiceImpl implements DemoService {
    @Override
    public String hello(String name) {
        return "fallback hello " + name;
    }
}
