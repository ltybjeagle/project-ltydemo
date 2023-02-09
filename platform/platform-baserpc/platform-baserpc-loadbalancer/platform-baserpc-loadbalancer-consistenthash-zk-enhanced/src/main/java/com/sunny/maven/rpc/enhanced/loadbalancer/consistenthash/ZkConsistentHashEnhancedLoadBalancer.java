package com.sunny.maven.rpc.enhanced.loadbalancer.consistenthash;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author SUNNY
 * @description: 基于Zookeeper的一致性Hash
 * @create: 2023-02-08 15:22
 */
@Slf4j
@SPIClass
public class ZkConsistentHashEnhancedLoadBalancer implements ServiceLoadBalancer<ServiceMeta> {
    private final static int VIRTUAL_NODE_SIZE = 10;
    private final static String VIRTUAL_NODE_SPLIT = "#";
    @Override
    public ServiceMeta select(List<ServiceMeta> servers, int hashCode, String sourceIp) {
        log.info("基于Zookeeper增强型的一致性Hash算法的负载均衡策略...");
        TreeMap<Integer, ServiceMeta> ring = makeConsistentHashRing(servers);
        return allocateNode(ring, hashCode);
    }

    private ServiceMeta allocateNode(TreeMap<Integer, ServiceMeta> ring, int hashCode) {
        Map.Entry<Integer, ServiceMeta> entry = ring.ceilingEntry(hashCode);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        if (entry == null) {
            throw new RuntimeException("not discover useful service, please register service in registry center.");
        }
        return entry.getValue();
    }

    private TreeMap<Integer, ServiceMeta> makeConsistentHashRing(List<ServiceMeta> servers) {
        TreeMap<Integer, ServiceMeta> ring = new TreeMap<>();
        for (ServiceMeta instance : servers) {
            for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
                ring.put((buildServiceInstanceKey(instance) + VIRTUAL_NODE_SPLIT + i).hashCode(), instance);
            }
        }
        return ring;
    }

    private String buildServiceInstanceKey(ServiceMeta instance) {
        return String.join(":", instance.getServiceAddr(), String.valueOf(instance.getServicePort()));
    }
}
