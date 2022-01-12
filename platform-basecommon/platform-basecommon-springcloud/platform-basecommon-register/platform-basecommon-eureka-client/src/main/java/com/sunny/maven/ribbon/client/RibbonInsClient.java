package com.sunny.maven.ribbon.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: Ribbon客户端
 * @create: 2021-12-10 12:02
 */
@Component
public class RibbonInsClient {

    private LoadBalancerClient loadBalancer;

    /**
     * 根据服务名获取服务信息
     * @param serviceName 服务名
     * @return String
     */
    public String getServiceInfo(String serviceName) {
        ServiceInstance serviceInstance = loadBalancer.choose(serviceName);
        return serviceInstance.toString();
    }

    /**
     * 构造函数
     * @param loadBalancer 负载对象
     */
    @Autowired
    public RibbonInsClient(LoadBalancerClient loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
}
