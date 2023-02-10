package com.sunny.maven.rpc.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.common.json.JsonUtils;
import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SUNNY
 * @description: 基于Nacos的注册服务
 * @create: 2023-02-09 15:29
 */
@SPIClass
public class NacosRegistryService implements RegistryService {
    private static final String METADATA_KEY = "serviceMeta";
    private NamingService namingService;
    /**
     * 负载均衡接口
     */
    private ServiceLoadBalancer<ServiceMeta> serviceLoadBalancer;
    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {
        String serviceName = RpcServiceHelper.buildServiceKey(
                serviceMeta.getServiceName(), serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup());
        Instance instance = new Instance();
        instance.setIp(serviceMeta.getServiceAddr());
        instance.setPort(serviceMeta.getServicePort());
        instance.addMetadata(METADATA_KEY, JsonUtils.toJson(serviceMeta));
        namingService.registerInstance(serviceName, instance);
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {
        String serviceName = RpcServiceHelper.buildServiceKey(
                serviceMeta.getServiceName(), serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup());
        Instance instance = new Instance();
        instance.setIp(serviceMeta.getServiceAddr());
        instance.setPort(serviceMeta.getServicePort());
        instance.addMetadata(METADATA_KEY, JsonUtils.toJson(serviceMeta));
        namingService.deregisterInstance(serviceName, instance);
    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode, String sourceIp) throws Exception {
        List<Instance> instances = namingService.getAllInstances(serviceName);
        List<ServiceMeta> serviceMetas = new CopyOnWriteArrayList<>();
        instances.forEach(instance ->
                serviceMetas.add(JsonUtils.parse(instance.getMetadata().get(METADATA_KEY), ServiceMeta.class)));
        return this.serviceLoadBalancer.select(serviceMetas, invokerHashCode, sourceIp);
    }

    @Override
    public void destroy() throws IOException {
        try {
            namingService.shutDown();
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(RegistryConfig registryConfig) throws Exception {
        namingService = NamingFactory.createNamingService(registryConfig.getRegistryAddr());
        this.serviceLoadBalancer = ExtensionLoader.getExtension(ServiceLoadBalancer.class,
                registryConfig.getRegistryLoadBalanceType());
    }
}
