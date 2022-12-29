package com.sunny.maven.rpc.test.scanner.consumer.service.impl;

import com.sunny.maven.rpc.annotation.RpcReference;
import com.sunny.maven.rpc.test.scanner.consumer.service.ConsumerBusinessService;
import com.sunny.maven.rpc.test.scanner.service.DemoService;

/**
 * @author SUNNY
 * @description: 服务消费者业务逻辑实现类
 * @create: 2022-12-23 18:03
 */
public class ConsumerBusinessServiceImpl implements ConsumerBusinessService {
    @RpcReference(registryType = "zookeeper", registryAddress = "127.0.0.1:2181", version = "1.0.0",
            group = "SUNNY")
    private DemoService demoService;
}
