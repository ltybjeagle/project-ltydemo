package com.sunny.maven.rpc.registry.eureka;

import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;

import java.io.IOException;

/**
 * @author SUNNY
 * @description: 基于Eureka的注册服务
 * @create: 2023-02-11 17:09
 */
public class EurekaRegistryService implements RegistryService {
    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode, String sourceIp) throws Exception {
        return null;
    }

    @Override
    public void destroy() throws IOException {

    }

    @Override
    public void init(RegistryConfig registryConfig) throws Exception {

    }
}
