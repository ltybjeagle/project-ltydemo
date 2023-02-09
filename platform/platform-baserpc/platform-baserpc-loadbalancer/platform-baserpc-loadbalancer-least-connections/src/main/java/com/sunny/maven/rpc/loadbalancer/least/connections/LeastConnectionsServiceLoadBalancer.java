package com.sunny.maven.rpc.loadbalancer.least.connections;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.loadbalancer.context.ConnectionsContext;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 基于最少连接数的负载均衡策略
 * @create: 2023-02-08 16:28
 */
@Slf4j
@SPIClass
public class LeastConnectionsServiceLoadBalancer implements ServiceLoadBalancer<ServiceMeta> {
    @Override
    public ServiceMeta select(List<ServiceMeta> servers, int hashCode, String sourceIp) {
        log.info("基于最少连接数的负载均衡策略...");
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        ServiceMeta serviceMeta = this.getNullServiceMeta(servers);
        if (serviceMeta == null) {
            serviceMeta = this.getServiceMeta(servers);
        }
        return serviceMeta;
    }

    private ServiceMeta getServiceMeta(List<ServiceMeta> servers) {
        ServiceMeta serviceMeta = servers.get(0);
        Integer serviceMetaCount = ConnectionsContext.getValue(serviceMeta);
        for (int i = 1; i < servers.size(); i++) {
            ServiceMeta meta = servers.get(i);
            Integer metaCount = ConnectionsContext.getValue(meta);
            if (serviceMetaCount > metaCount) {
                serviceMetaCount = metaCount;
                serviceMeta = meta;
            }
        }
        return serviceMeta;
    }

    /**
     * 获取服务元数据列表中连接数为空的元数据，说明没有连接
     */
    private ServiceMeta getNullServiceMeta(List<ServiceMeta> servers) {
        for (int i = 0; i < servers.size(); i++) {
            ServiceMeta serviceMeta = servers.get(i);
            if (ConnectionsContext.getValue(serviceMeta) == null) {
                return serviceMeta;
            }
        }
        return null;
    }
}
