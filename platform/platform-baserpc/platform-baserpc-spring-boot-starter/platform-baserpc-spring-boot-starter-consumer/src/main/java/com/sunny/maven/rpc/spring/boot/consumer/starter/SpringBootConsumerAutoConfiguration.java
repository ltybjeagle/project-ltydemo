package com.sunny.maven.rpc.spring.boot.consumer.starter;

import com.sunny.maven.rpc.consumer.RpcClient;
import com.sunny.maven.rpc.spring.boot.consumer.config.SpringBootConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: 服务消费者的自动配置类
 * @create: 2023-02-17 14:08
 */
@Configuration
@EnableConfigurationProperties
public class SpringBootConsumerAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "baserpc.sunny.consumer")
    public SpringBootConsumerConfig springBootConsumerConfig() {
        return new SpringBootConsumerConfig();
    }

    @Bean
    public RpcClient rpcClient(final SpringBootConsumerConfig springBootConsumerConfig) {
        return new RpcClient(springBootConsumerConfig.getRegistryAddress(), springBootConsumerConfig.getRegistryType(),
                springBootConsumerConfig.getVersion(), springBootConsumerConfig.getGroup(),
                springBootConsumerConfig.getSerializationType(), springBootConsumerConfig.getLoadBalanceType(),
                springBootConsumerConfig.getTimeout(), springBootConsumerConfig.getProxy(),
                springBootConsumerConfig.isAsync(), springBootConsumerConfig.isOneWay(),
                springBootConsumerConfig.getHeartbeatInterval(),
                springBootConsumerConfig.getScanNotActiveChannelInterval(), springBootConsumerConfig.getRetryInterval(),
                springBootConsumerConfig.getRetryTimes());
    }
}
