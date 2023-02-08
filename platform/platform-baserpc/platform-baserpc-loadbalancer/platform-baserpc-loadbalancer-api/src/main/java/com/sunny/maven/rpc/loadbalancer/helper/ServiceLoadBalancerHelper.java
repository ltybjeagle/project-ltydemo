package com.sunny.maven.rpc.loadbalancer.helper;

import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SUNNY
 * @description: 服务负载均衡辅助类
 * @create: 2023-02-08 11:21
 */
public class ServiceLoadBalancerHelper {
    private static volatile List<ServiceMeta> cacheServiceMeta = new CopyOnWriteArrayList<>();

    public static List<ServiceMeta> getServiceMetaList(List<ServiceInstance<ServiceMeta>> serviceInstances) {
        if (serviceInstances == null || serviceInstances.isEmpty() ||
                serviceInstances.size() == cacheServiceMeta.size()) {
            return cacheServiceMeta;
        }
        // 先清空cacheServiceMeta中的数据
        cacheServiceMeta.clear();
        serviceInstances.forEach(serviceInstance -> cacheServiceMeta.add(serviceInstance.getPayload()));
        return cacheServiceMeta;
    }
}
