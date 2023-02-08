package com.sunny.maven.rpc.enhanced.loadbalancer.random.weight;

import com.sunny.maven.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @author SUNNY
 * @description: 增强型加权随机
 * @create: 2023-02-08 12:14
 */
@Slf4j
@SPIClass
public class RandomWeightServiceEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {
    @Override
    public ServiceMeta select(List<ServiceMeta> servers, int hashCode, String sourceIp) {
        log.info("基于增强型加权随机算法的负载均衡策略...");
        servers = this.getWeightServiceMetaList(servers);
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(servers.size());
        return servers.get(index);
    }
}
