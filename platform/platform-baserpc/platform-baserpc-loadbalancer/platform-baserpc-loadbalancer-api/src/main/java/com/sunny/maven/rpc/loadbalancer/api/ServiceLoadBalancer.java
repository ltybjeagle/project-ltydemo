package com.sunny.maven.rpc.loadbalancer.api;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.spi.annotation.SPI;

import java.util.List;

/**
 * @author SUNNY
 * @description: 负载均衡接口
 * @create: 2023-01-05 16:23
 */
@SPI(RpcConstants.SERVICE_LOAD_BALANCER_RANDOM)
public interface ServiceLoadBalancer<T> {

    /**
     * 以负载均衡的方式选取一个服务节点
     * @param servers 服务列表
     * @param hashCode Hash值
     * @return 可用的服务节点
     */
    T select(List<T> servers, int hashCode);
}
