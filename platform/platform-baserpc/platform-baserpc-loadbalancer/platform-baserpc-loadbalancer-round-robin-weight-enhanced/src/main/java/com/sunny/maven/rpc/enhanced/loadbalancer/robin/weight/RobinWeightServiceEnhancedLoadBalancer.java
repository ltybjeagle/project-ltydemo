package com.sunny.maven.rpc.enhanced.loadbalancer.robin.weight;

import com.sunny.maven.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SUNNY
 * @description: 增强型加权轮询负载均衡
 * @create: 2023-02-08 14:08
 */
@Slf4j
@SPIClass
public class RobinWeightServiceEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {
    private volatile AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public ServiceMeta select(List<ServiceMeta> servers, int hashCode, String sourceIp) {
        log.info("基于增强型加权轮询算法的负载均衡策略...");
        servers = this.getWeightServiceMetaList(servers);
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        int index = atomicInteger.incrementAndGet();
        if (index >= (Integer.MAX_VALUE - 10000)) {
            atomicInteger.set(0);
        }
        return servers.get(index % servers.size());
    }
}
