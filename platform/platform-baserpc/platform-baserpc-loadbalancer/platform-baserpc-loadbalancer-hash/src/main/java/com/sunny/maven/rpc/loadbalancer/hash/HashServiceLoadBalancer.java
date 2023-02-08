package com.sunny.maven.rpc.loadbalancer.hash;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 基于Hash算法的负载均衡策略
 * @create: 2023-02-07 14:05
 */
@Slf4j
@SPIClass
public class HashServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    @Override
    public T select(List<T> servers, int hashCode, String sourceIp) {
        log.info("基于Hash算法的负载均衡策略...");
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        int index = Math.abs(hashCode) % servers.size();
        return servers.get(index);
    }
}
