package com.liuty.maven.controller;

import com.liuty.maven.entity.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class WebDemoUserController {

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public DiscoveryClient discoveryClient;

    @GetMapping("/user/{id}")
    public DemoUser findById(@PathVariable String id) {
        return this.restTemplate.getForObject("http://localhost:8000/" + id, DemoUser.class);
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
