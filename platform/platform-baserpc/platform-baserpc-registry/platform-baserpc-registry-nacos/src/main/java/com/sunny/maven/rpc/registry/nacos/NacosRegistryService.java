package com.sunny.maven.rpc.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.registry.nacos.instance.RegisterInstance;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SUNNY
 * @description: 基于Nacos的注册服务
 * @create: 2023-02-09 15:29
 */
@SPIClass
public class NacosRegistryService implements RegistryService {
    private NamingService namingService;
    /**
     * 负载均衡接口
     */
    private ServiceLoadBalancer<ServiceMeta> serviceLoadBalancer;
    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {
        String serviceName = RpcServiceHelper.buildServiceKey(
                serviceMeta.getServiceName(), serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup());
        RegisterInstance<ServiceMeta> instance = new RegisterInstance.RegisterInstanceBuilder<ServiceMeta>(
                serviceMeta.getServiceAddr(), serviceMeta.getServicePort()).payload(serviceMeta).build();
        namingService.registerInstance(serviceName, instance);
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {
        String serviceName = RpcServiceHelper.buildServiceKey(
                serviceMeta.getServiceName(), serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup());
        RegisterInstance<ServiceMeta> instance = new RegisterInstance.RegisterInstanceBuilder<ServiceMeta>(
                serviceMeta.getServiceAddr(), serviceMeta.getServicePort()).payload(serviceMeta).build();
        namingService.deregisterInstance(serviceName, instance);
    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode, String sourceIp) throws Exception {
        List<Instance> instances = namingService.getAllInstances(serviceName);
        List<ServiceMeta> serviceMetas = new CopyOnWriteArrayList<>();
        instances.forEach(instance -> serviceMetas.add(((RegisterInstance<ServiceMeta>) instance).getPayload()));
        return this.serviceLoadBalancer.select(serviceMetas, invokerHashCode, sourceIp);
    }

    @Override
    public ServiceMeta select(List<ServiceMeta> serviceMetaList, int invokerHashCode, String sourceIp) {
        return this.serviceLoadBalancer.select(serviceMetaList, invokerHashCode, sourceIp);
    }

    @Override
    public List<ServiceMeta> discoveryAll() throws Exception {
        List<ServiceMeta> serviceMetaList = new ArrayList<>();
        List<ServiceInfo> serviceInfos = namingService.getSubscribeServices();
        if (serviceInfos == null || serviceInfos.isEmpty()) {
            return serviceMetaList;
        }
        for (ServiceInfo serviceInfo : serviceInfos) {
            List<Instance> instances = serviceInfo.getHosts();
            instances.forEach(instance -> serviceMetaList.add(((RegisterInstance<ServiceMeta>) instance).getPayload()));
        }
        return serviceMetaList;
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
