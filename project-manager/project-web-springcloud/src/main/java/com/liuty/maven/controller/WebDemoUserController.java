package com.liuty.maven.controller;

import com.liuty.maven.entity.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class WebDemoUserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/user/{id}")
    public DemoUser findById(@PathVariable String id) {
        return this.restTemplate.getForObject("http://microservice-provider-user/" + id, DemoUser.class);
    }

    /**
     * 日志记录负载均衡调用记录
     */
    @GetMapping("/log-instance")
    public void logUserInstance() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
        System.out.println("调用服务：" + serviceInstance.getServiceId() + "、" + serviceInstance.getHost()
                + "、" + serviceInstance.getPort());
    }
    /**
     * 查询microservice-provider-user服务的信息并返回
     * @return
     */
    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("microservice-provider-user");
    }
}
