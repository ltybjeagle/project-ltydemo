package com.sunny.maven.rpc.demo.provider.impl;

import com.sunny.maven.rpc.annotation.RpcService;
import com.sunny.maven.rpc.common.exception.RpcException;
import com.sunny.maven.rpc.demo.api.DemoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: DemoService实现类
 * @create: 2022-12-27 11:24
 */
@Slf4j
@RpcService(interfaceClass = DemoService.class,
        interfaceClassName = "com.sunny.maven.rpc.test.api.DemoService",
        version = "1.0.0", group = "SUNNY", weight = 2)
public class ProviderDemoServiceImpl implements DemoService {
    @Override
    public String hello(String name) {
        log.info("调用hello方法传入的参数为====>>> {}", name);
        if ("SUNNY".equals(name)) {
            throw new RpcException("rpc provider throws exception");
        }
        return "hello " + name;
    }
}
