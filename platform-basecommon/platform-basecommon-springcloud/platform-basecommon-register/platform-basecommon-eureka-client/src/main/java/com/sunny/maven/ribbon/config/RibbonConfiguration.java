package com.sunny.maven.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.sunny.maven.ribbon.annotation.MyLoadBalanced;
import com.sunny.maven.ribbon.rule.MyRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author SUNNY
 * @description: 负载配置类
 * @create: 2021-11-26 15:33
 */
@Configuration
public class RibbonConfiguration {

    /**
     * 设置负载策略
     * @return IRule
     */
    @Bean
    public IRule getMyRule() {
        // 自定义负载策略
//        return new MyRule();
        // 轮询负载策略
        return new RoundRobinRule();
    }

    @Bean
    @LoadBalanced
    //@MyLoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
