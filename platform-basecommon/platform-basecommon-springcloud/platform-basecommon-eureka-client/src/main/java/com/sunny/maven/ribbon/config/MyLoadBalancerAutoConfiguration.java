package com.sunny.maven.ribbon.config;

import com.sunny.maven.ribbon.annotation.MyLoadBalanced;
import com.sunny.maven.ribbon.interceptor.MyLoadBalancerInterceptor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author SUNNY
 * @description: 自定义负载配置类
 * @create: 2021-12-08 18:00
 */
@Configuration
public class MyLoadBalancerAutoConfiguration {
    @MyLoadBalanced
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Collections.emptyList();
    private LoadBalancerClient loadBalancer;
    @Bean
    public MyLoadBalancerInterceptor myLoadBalancerInterceptor() {
        return new MyLoadBalancerInterceptor(loadBalancer);
    }
    @Bean
    public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer() {
        return () -> {
            for (RestTemplate restTemplate : MyLoadBalancerAutoConfiguration.this.restTemplates){
                List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
                list.add(myLoadBalancerInterceptor());
                restTemplate.setInterceptors(list);
            }
        };
    }
    @Autowired
    public MyLoadBalancerAutoConfiguration(LoadBalancerClient loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
}
