package com.sunny.maven.rpc.test.provider.service.impl;

import com.sunny.maven.rpc.annotation.RpcService;
import com.sunny.maven.rpc.test.provider.service.DemoService;

/**
 * @author SUNNY
 * @description: DemoService实现类
 * @create: 2022-12-27 11:24
 */
@RpcService(interfaceClass = DemoService.class,
        interfaceClassName = "com.sunny.maven.rpc.test.provider.service.DemoService",
        version = "1.0.0", group = "SUNNY")
public class ProviderDemoServiceImpl implements DemoService {
}
