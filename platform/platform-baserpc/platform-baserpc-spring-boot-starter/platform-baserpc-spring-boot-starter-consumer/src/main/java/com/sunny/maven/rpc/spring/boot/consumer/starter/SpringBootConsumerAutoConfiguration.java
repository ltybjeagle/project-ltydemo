package com.sunny.maven.rpc.spring.boot.consumer.starter;

import com.sunny.maven.rpc.common.utils.StringUtils;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.consumer.RpcClient;
import com.sunny.maven.rpc.consumer.spring.RpcReferenceBean;
import com.sunny.maven.rpc.consumer.spring.config.RpcConsumerSpringContext;
import com.sunny.maven.rpc.spring.boot.consumer.config.SpringBootConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: RPC 服务提供者的自动配置类，配置优先级：
 *      服务消费者字段上配置的@RpcReference注解属性 > yml文件 > @RpcReference默认注解属性
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
        return parseRpcClient(springBootConsumerConfig);
    }

    private RpcClient parseRpcClient(final SpringBootConsumerConfig springBootConsumerConfig) {
        RpcReferenceBean referenceBean = getRpcReferenceBean(springBootConsumerConfig);
        referenceBean.init();
        return referenceBean.getRpcClient();
    }

    /**
     * 首先从Spring IOC容器中获取RpcReferenceBean，
     * 如果存在RpcReferenceBean，部分RpcReferenceBean的字段为空，则使用springBootConsumerConfig字段进行填充
     * 如果不存在RpcReferenceBean，则使用springBootConsumerConfig构建RpcReferenceBean
     */
    private RpcReferenceBean getRpcReferenceBean(final SpringBootConsumerConfig springBootConsumerConfig) {
        ApplicationContext context = RpcConsumerSpringContext.getInstance().getContext();
        RpcReferenceBean referenceBean = context.getBean(RpcReferenceBean.class);
        if (StringUtils.isEmpty(referenceBean.getGroup()) ||
                (RpcConstants.RPC_COMMON_DEFAULT_GROUP.equals(referenceBean.getGroup()) &&
                        !StringUtils.isEmpty(springBootConsumerConfig.getGroup()))) {
            referenceBean.setGroup(springBootConsumerConfig.getGroup());
        }
        if (StringUtils.isEmpty(referenceBean.getVersion()) ||
                (RpcConstants.RPC_COMMON_DEFAULT_VERSION.equals(referenceBean.getVersion()) &&
                        !StringUtils.isEmpty(springBootConsumerConfig.getVersion()))) {
            referenceBean.setVersion(springBootConsumerConfig.getVersion());
        }
        if (StringUtils.isEmpty(referenceBean.getRegistryType()) ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_REGISTRYTYPE.equals(referenceBean.getRegistryType()) &&
                        !StringUtils.isEmpty(springBootConsumerConfig.getRegistryType()))) {
            referenceBean.setRegistryType(springBootConsumerConfig.getRegistryType());
        }
        if (StringUtils.isEmpty(referenceBean.getLoadbalancerType()) ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_LOADBALANCETYPE.equals(referenceBean.getLoadbalancerType()) &&
                        !StringUtils.isEmpty(springBootConsumerConfig.getLoadBalanceType()))) {
            referenceBean.setLoadbalancerType(springBootConsumerConfig.getLoadBalanceType());
        }
        if (StringUtils.isEmpty(referenceBean.getSerializationType()) ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_SERIALIZATIONTYPE.equals(referenceBean.getSerializationType()) &&
                        !StringUtils.isEmpty(springBootConsumerConfig.getSerializationType()))) {
            referenceBean.setSerializationType(springBootConsumerConfig.getSerializationType());
        }
        if (StringUtils.isEmpty(referenceBean.getRegistryAddress()) ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_REGISTRYADDRESS.equals(referenceBean.getRegistryAddress()) &&
                        !StringUtils.isEmpty(springBootConsumerConfig.getRegistryAddress()))) {
            referenceBean.setRegistryAddress(springBootConsumerConfig.getRegistryAddress());
        }
        if (referenceBean.getTimeout() <= 0 ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_TIMEOUT == referenceBean.getTimeout() &&
                        springBootConsumerConfig.getTimeout() > 0)) {
            referenceBean.setTimeout(springBootConsumerConfig.getTimeout());
        }
        if (!referenceBean.isAsync()) {
            referenceBean.setAsync(springBootConsumerConfig.isAsync());
        }
        if (!referenceBean.isOneWay()) {
            referenceBean.setOneWay(springBootConsumerConfig.isOneWay());
        }
        if (StringUtils.isEmpty(referenceBean.getProxy()) ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_PROXY.equals(referenceBean.getProxy()) &&
                        !StringUtils.isEmpty(springBootConsumerConfig.getProxy()))) {
            referenceBean.setProxy(springBootConsumerConfig.getProxy());
        }
        if (referenceBean.getHeartbeatInterval() <= 0 ||
                (RpcConstants.RPC_COMMON_DEFAULT_HEARTBEATINTERVAL == referenceBean.getHeartbeatInterval() &&
                        springBootConsumerConfig.getHeartbeatInterval() > 0)) {
            referenceBean.setHeartbeatInterval(springBootConsumerConfig.getHeartbeatInterval());
        }
        if (referenceBean.getScanNotActiveChannelInterval() <= 0 ||
                (RpcConstants.RPC_COMMON_DEFAULT_SCANNOTACTIVECHANNELINTERVAL ==
                        referenceBean.getScanNotActiveChannelInterval() &&
                        springBootConsumerConfig.getScanNotActiveChannelInterval() > 0)) {
            referenceBean.setScanNotActiveChannelInterval(springBootConsumerConfig.getScanNotActiveChannelInterval());
        }
        if (referenceBean.getRetryInterval() <= 0 ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_RETRYINTERVAL == referenceBean.getRetryInterval() &&
                        springBootConsumerConfig.getRetryInterval() > 0)) {
            referenceBean.setRetryInterval(springBootConsumerConfig.getRetryInterval());
        }
        if (referenceBean.getRetryTimes() <= 0 ||
                (RpcConstants.RPC_REFERENCE_DEFAULT_RETRYTIMES == referenceBean.getRetryTimes() &&
                        springBootConsumerConfig.getRetryTimes() > 0)) {
            referenceBean.setRetryTimes(springBootConsumerConfig.getRetryTimes());
        }
        if (!referenceBean.isEnableResultCache()) {
            referenceBean.setEnableResultCache(springBootConsumerConfig.isEnableResultCache());
        }
        if (referenceBean.getResultCacheExpire() <= 0 ||
                (RpcConstants.RPC_SCAN_RESULT_CACHE_EXPIRE == referenceBean.getResultCacheExpire() &&
                        springBootConsumerConfig.getResultCacheExpire() > 0)) {
            referenceBean.setResultCacheExpire(springBootConsumerConfig.getResultCacheExpire());
        }
        return referenceBean;
    }
}
