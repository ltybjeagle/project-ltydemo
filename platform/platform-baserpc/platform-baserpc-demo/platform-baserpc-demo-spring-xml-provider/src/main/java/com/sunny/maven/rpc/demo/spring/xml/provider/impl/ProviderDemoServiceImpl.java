package com.sunny.maven.rpc.demo.spring.xml.provider.impl;

import com.sunny.maven.rpc.annotation.RpcService;
import com.sunny.maven.rpc.demo.api.DemoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: DemoService实现类
 * @create: 2023-02-14 16:37
 */
@Slf4j
@RpcService(interfaceClass = DemoService.class, interfaceClassName = "com.sunny.maven.rpc.demo.api.DemoService",
        version = "1.0.0", group = "SUNNY", weight = 2)
public class ProviderDemoServiceImpl implements DemoService {
    @Override
    public String hello(String name) {
        log.info("调用hello方法传入的参数为===>>>{}", name);
        return "hello " + name;
    }
}
