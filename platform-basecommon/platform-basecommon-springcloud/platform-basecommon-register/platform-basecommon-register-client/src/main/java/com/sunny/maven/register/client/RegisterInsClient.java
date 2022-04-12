package com.sunny.maven.register.client;

import com.sunny.maven.register.ServiceInsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: EurekaClient工具类
 * @create: 2021-11-30 14:12
 */
@Component
public class RegisterInsClient implements ServiceInsClient {

    private DiscoveryClient discoveryClient;

    /**
     * 根据服务名查询服务实例信息
     * @param serviceName 服务名
     * @return Object
     */
    @Override
    public Object getServiceInsByName(String serviceName) {
        return discoveryClient.getInstances(serviceName);
    }

    /**
     * 构造函数
     * @param discoveryClient 注册中心客户端
     */
    @Autowired
    public RegisterInsClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }
}
