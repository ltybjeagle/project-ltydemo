package com.sunny.maven.rpc.loadbalancer.consistenthash;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author SUNNY
 * @description: 基于Zookeeper的一致性Hash
 * @create: 2023-02-07 16:23
 */
@Slf4j
@SPIClass
public class ZkConsistentHashLoadBalancer<T> implements ServiceLoadBalancer<T> {
    private final static int VIRTUAL_NODE_SIZE = 10;
    private final static String VIRTUAL_NODE_SPLIT = "#";
    @Override
    public T select(List<T> servers, int hashCode, String sourceIp) {
        log.info("基于Zookeeper的一致性Hash算法的负载均衡策略...");
        TreeMap<Integer, T> ring = makeConsistentHashRing(servers);
        return allocateNode(ring, hashCode);
    }

    private T allocateNode(TreeMap<Integer, T> ring, int hashCode) {
        Map.Entry<Integer, T> entry = ring.ceilingEntry(hashCode);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        if (entry == null) {
            throw new RuntimeException("not discover useful service, please register service in registry center.");
        }
        return entry.getValue();
    }

    private TreeMap<Integer, T> makeConsistentHashRing(List<T> servers) {
        TreeMap<Integer, T> ring = new TreeMap<>();
        for (T instance : servers) {
            for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
                ring.put((buildServiceInstanceKey(instance) + VIRTUAL_NODE_SPLIT + i).hashCode(), instance);
            }
        }
        return ring;
    }

    private String buildServiceInstanceKey(T instance) {
        return Objects.toString(instance);
    }
}
