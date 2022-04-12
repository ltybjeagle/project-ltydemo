package com.sunny.maven.register.configuration;

import com.sunny.maven.register.listener.RegisterApplicationListener;
import com.sunny.maven.register.listener.RegisterPreApplicationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author SUNNY
 * @description: 注册中心配置类
 * @create: 2022-03-02 17:41
 */
@Configuration
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.sunny.maven.register.client"})
public class RegisterConfiguration {

    private LoadBalancerClient loadBalancer;
    /**
     * 负载均衡器客户端
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 实例注册事件（注册前）
     * @return RegisterPreApplicationListener
     */
    @Bean
    public RegisterPreApplicationListener registerPreApplicationListener() {
        return new RegisterPreApplicationListener();
    }

    /**
     * 实例注册事件（注册后）
     * @return RegisterApplicationListener
     */
    @Bean
    public RegisterApplicationListener registerApplicationListener() {
        return new RegisterApplicationListener();
    }

    @Autowired
    public RegisterConfiguration(LoadBalancerClient loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
}
