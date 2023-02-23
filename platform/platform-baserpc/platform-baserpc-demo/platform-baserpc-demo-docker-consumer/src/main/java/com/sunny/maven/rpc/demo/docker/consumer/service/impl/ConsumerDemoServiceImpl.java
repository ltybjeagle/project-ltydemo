package com.sunny.maven.rpc.demo.docker.consumer.service.impl;

import com.sunny.maven.rpc.annotation.RpcReference;
import com.sunny.maven.rpc.demo.api.DemoService;
import com.sunny.maven.rpc.demo.docker.consumer.service.ConsumerDemoService;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 服务消费者Service实现类
 * @create: 2023-02-15 14:33
 */
@Service
public class ConsumerDemoServiceImpl implements ConsumerDemoService {
    @RpcReference(registryType = "zookeeper", registryAddress = "192.168.184.102:2181", loadBalanceType = "zkconsistenthash",
            version = "1.0.0", group = "SUNNY", serializationType = "protostuff", proxy = "asm", timeout = 30000,
            async = false, oneway = false)
    private DemoService demoService;
    @Override
    public String hello(String name) {
        return demoService.hello(name);
    }

    public DemoService getDemoService() {
        return demoService;
    }

    public void setDemoService(DemoService demoService) {
        this.demoService = demoService;
    }
}
