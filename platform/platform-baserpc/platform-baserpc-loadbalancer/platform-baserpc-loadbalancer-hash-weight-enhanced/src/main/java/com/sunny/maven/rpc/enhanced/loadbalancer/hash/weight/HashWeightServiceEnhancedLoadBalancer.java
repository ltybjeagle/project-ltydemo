package com.sunny.maven.rpc.enhanced.loadbalancer.hash.weight;

import com.sunny.maven.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 增强型加权Hash算法负载均衡
 * @create: 2023-02-08 14:47
 */
@Slf4j
@SPIClass
public class HashWeightServiceEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {
    @Override
    public ServiceMeta select(List<ServiceMeta> servers, int hashCode, String sourceIp) {
        log.info("基于增强型加权Hash算法的负载均衡策略...");
        servers = this.getWeightServiceMetaList(servers);
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        int index = Math.abs(hashCode) % servers.size();
        return servers.get(index);
    }
}
