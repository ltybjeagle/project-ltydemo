package com.sunny.maven.rpc.loadbalancer.random;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @author SUNNY
 * @description: 基于随机算法的负载均衡策略
 * @create: 2023-01-05 16:30
 */
@Slf4j
@SPIClass
public class RandomServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    @Override
    public T select(List<T> servers, int hashCode) {
        log.info("基于随机算法的负载均衡策略");
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(servers.size());
        return servers.get(index);
    }
}
