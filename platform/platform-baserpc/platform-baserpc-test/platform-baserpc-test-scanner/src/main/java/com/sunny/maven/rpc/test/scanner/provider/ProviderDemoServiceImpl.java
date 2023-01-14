package com.sunny.maven.rpc.test.scanner.provider;

import com.sunny.maven.rpc.annotation.RpcService;
import com.sunny.maven.rpc.test.scanner.service.DemoService;

/**
 * @author SUNNY
 * @description: DemoService实现类
 * @create: 2022-12-23 17:54
 */
@RpcService(interfaceClass = DemoService.class,
        interfaceClassName = "com.sunny.maven.rpc.test.scanner.service.DemoService", version = "1.0.0",
        group = "SUNNY")
public class ProviderDemoServiceImpl implements DemoService {
}
