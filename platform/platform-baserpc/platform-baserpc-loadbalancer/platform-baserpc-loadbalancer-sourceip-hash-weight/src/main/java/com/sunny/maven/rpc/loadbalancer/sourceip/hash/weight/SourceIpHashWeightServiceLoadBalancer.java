package com.sunny.maven.rpc.loadbalancer.sourceip.hash.weight;

import com.sunny.maven.rpc.loadbalancer.api.ServiceLoadBalancer;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author SUNNY
 * @description: 基于源IP地址加权Hash的负载均衡策略
 * @create: 2023-02-07 15:45
 */
@Slf4j
@SPIClass
public class SourceIpHashWeightServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    @Override
    public T select(List<T> servers, int hashCode, String sourceIp) {
        log.info("基于源IP地址加权Hash的负载均衡策略...");
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        // 传入的IP地址为空，则默认返回第一个服务实例
        if (StringUtils.isEmpty(sourceIp)) {
            return servers.get(0);
        }
        hashCode = Math.abs(hashCode);
        int count = hashCode % servers.size();
        if (count == 0) {
            count = servers.size();
        }
        int resultHashCode = Math.abs(sourceIp.hashCode() + hashCode);
        return servers.get(resultHashCode % count);
    }
}
