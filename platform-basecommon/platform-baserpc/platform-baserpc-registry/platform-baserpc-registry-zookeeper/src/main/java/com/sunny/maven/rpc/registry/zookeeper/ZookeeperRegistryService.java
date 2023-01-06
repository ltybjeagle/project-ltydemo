package com.sunny.maven.rpc.registry.zookeeper;

import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.loadbalancer.random.RandomServiceLoadBalancer;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author SUNNY
 * @description: 基于Zookeeper的注册服务
 * @create: 2023-01-03 21:59
 */
public class ZookeeperRegistryService implements RegistryService {
    public static final int BASE_SLEEP_TIME_MS = 1000;
    public static final int MAX_RETRIES = 3;
    public static final String ZK_BASE_PATH = "platform_rpc";

    private ServiceDiscovery<ServiceMeta> serviceDiscovery;
    /**
     * 负载均衡接口
     */
    private ServiceLoadBalancer<ServiceInstance<ServiceMeta>> serviceLoadBalancer;

    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {
        ServiceInstance<ServiceMeta> serviceInstance = ServiceInstance.<ServiceMeta>builder().
                name(RpcServiceHelper.buildServiceKey(
                        serviceMeta.getServiceName(), serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup())).
                address(serviceMeta.getServiceAddr()).port(serviceMeta.getServicePort()).payload(serviceMeta).build();
        serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {
        ServiceInstance<ServiceMeta> serviceInstance = ServiceInstance.<ServiceMeta>builder().
                name(serviceMeta.getServiceName()).address(serviceMeta.getServiceAddr()).
                port(serviceMeta.getServicePort()).payload(serviceMeta).build();
        serviceDiscovery.unregisterService(serviceInstance);
    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode) throws Exception {
        Collection<ServiceInstance<ServiceMeta>> serviceInstances = serviceDiscovery.queryForInstances(serviceName);
        ServiceInstance<ServiceMeta> instance = serviceLoadBalancer.select(
                (List<ServiceInstance<ServiceMeta>>) serviceInstances, invokerHashCode);
        if (instance != null) {
            return instance.getPayload();
        }
        return null;
    }

    @Override
    public void destroy() throws IOException {
        serviceDiscovery.close();
    }

    @Override
    public void init(RegistryConfig registryConfig) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(registryConfig.getRegistryAddr(),
                new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES));
        client.start();
        JsonInstanceSerializer<ServiceMeta> serializer = new JsonInstanceSerializer<>(ServiceMeta.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceMeta.class).
                client(client).serializer(serializer).basePath(ZK_BASE_PATH).build();
        this.serviceDiscovery.start();
        //TODO 默认创建基于随机算法的负载均衡策略，后续基于SPI扩展
        this.serviceLoadBalancer = new RandomServiceLoadBalancer<>();
    }
}
