package com.sunny.maven.rpc.spring.boot.provider.starter;

import com.sunny.maven.rpc.provider.spring.RpcSpringServer;
import com.sunny.maven.rpc.spring.boot.provider.config.SpringBootProviderConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: 服务提供者的自动配置类
 * @create: 2023-02-16 13:49
 */
@Configuration
@EnableConfigurationProperties
public class SpringBootProviderAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "baserpc.sunny.provider")
    public SpringBootProviderConfig springBootProviderConfig() {
        return new SpringBootProviderConfig();
    }

    @Bean
    public RpcSpringServer rpcSpringServer(SpringBootProviderConfig springBootProviderConfig) {
        return new RpcSpringServer(springBootProviderConfig.getServerAddress(),
                springBootProviderConfig.getServerRegistryAddress(), springBootProviderConfig.getReflectType(),
                springBootProviderConfig.getRegistryAddress(), springBootProviderConfig.getRegistryType(),
                springBootProviderConfig.getRegistryLoadBalancerType(), springBootProviderConfig.getHeartbeatInterval(),
                springBootProviderConfig.getScanNotActiveChannelInterval(),
                springBootProviderConfig.isEnableResultCache(), springBootProviderConfig.getResultCacheExpire());
    }
}
