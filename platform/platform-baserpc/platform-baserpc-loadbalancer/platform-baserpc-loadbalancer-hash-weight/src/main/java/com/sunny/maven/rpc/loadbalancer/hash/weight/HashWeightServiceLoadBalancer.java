package com.sunny.maven.rpc.loadbalancer.hash.weight;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 基于加权Hash算法负载均衡策略
 * @create: 2023-02-07 14:21
 */
@Slf4j
@SPIClass
public class HashWeightServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    @Override
    public T select(List<T> servers, int hashCode, String sourceIp) {
        log.info("基于加权Hash算法的负载均衡策略...");
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        hashCode = Math.abs(hashCode);
        int count = hashCode % servers.size();
        if (count <= 0) {
            count = servers.size();
        }
        int index = hashCode % count;
        return servers.get(index);
    }
}
