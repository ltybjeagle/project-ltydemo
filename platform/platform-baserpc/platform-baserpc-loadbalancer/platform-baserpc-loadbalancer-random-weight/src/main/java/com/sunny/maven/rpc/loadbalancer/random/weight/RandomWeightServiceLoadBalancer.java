package com.sunny.maven.rpc.loadbalancer.random.weight;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @author SUNNY
 * @description: 加权随机
 * @create: 2023-02-03 16:13
 */
@Slf4j
@SPIClass
public class RandomWeightServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    @Override
    public T select(List<T> servers, int hashCode, String sourceIp) {
        log.info("基于加权随机算法的负载均衡策略...");
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        hashCode = Math.abs(hashCode);
        int count  = hashCode % servers.size();
        if (count <= 1) {
            count = servers.size();
        }
        Random random = new Random();
        int index = random.nextInt(count);
        return servers.get(index);
    }
}
