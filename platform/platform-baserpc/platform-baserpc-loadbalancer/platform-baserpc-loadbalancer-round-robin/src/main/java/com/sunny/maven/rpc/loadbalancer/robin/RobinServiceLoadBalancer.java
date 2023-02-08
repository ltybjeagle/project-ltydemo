package com.sunny.maven.rpc.loadbalancer.robin;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SUNNY
 * @description: 基于轮询算法的负载均衡策略
 * @create: 2023-02-07 11:10
 */
@Slf4j
@SPIClass
public class RobinServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    private volatile AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public T select(List<T> servers, int hashCode, String sourceIp) {
        log.info("基于轮询算法的负载均衡策略...");
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        int count = servers.size();
        int index = atomicInteger.incrementAndGet();
        if (index >= (Integer.MAX_VALUE - 10000)) {
            atomicInteger.set(0);
        }
        return servers.get(index % count);
    }
}
