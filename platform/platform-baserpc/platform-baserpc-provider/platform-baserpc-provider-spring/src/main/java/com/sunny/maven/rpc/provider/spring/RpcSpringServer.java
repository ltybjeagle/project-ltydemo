package com.sunny.maven.rpc.provider.spring;

import com.sunny.maven.rpc.annotation.RpcService;
import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.provider.common.server.base.BaseServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author SUNNY
 * @description: 基于Spring启动RPC服务
 * @create: 2023-02-14 13:58
 */
@Slf4j
public class RpcSpringServer extends BaseServer implements ApplicationContextAware, InitializingBean {

    public RpcSpringServer(String serverAddress, String serverRegistryAddress, String reflectType,
                           String registryAddress, String registryType, String registryLoadBalanceType,
                           int heartbeatInterval, int scanNotActiveChannelInterval, boolean enableResultCache,
                           int resultCacheExpire, int corePoolSize, int maximumPoolSize, String flowType,
                           int maxConnections, String disuseStrategyType, boolean enableBuffer, int bufferSize) {
        super(serverAddress, serverRegistryAddress, reflectType, registryAddress, registryType, registryLoadBalanceType,
                heartbeatInterval, scanNotActiveChannelInterval, enableResultCache, resultCacheExpire, corePoolSize,
                maximumPoolSize, flowType, maxConnections, disuseStrategyType, enableBuffer, bufferSize);
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        this.startNettyServer();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                ServiceMeta serviceMeta = new ServiceMeta(this.getServiceName(rpcService), rpcService.version(),
                        serverRegistryHost, serverRegistryPort, rpcService.group(), this.getWeight(rpcService.weight()));
                handlerMap.put(RpcServiceHelper.buildServiceKey(serviceMeta.getServiceName(),
                        serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup()), serviceBean);
                try {
                    registryService.register(serviceMeta);
                } catch (Exception e) {
                    log.error("rpc server init spring exception:{}", e.getMessage());
                }
            }
        }
    }

    private int getWeight(int weight) {
        if (weight < RpcConstants.SERVICE_WEIGHT_MIN) {
            weight = RpcConstants.SERVICE_WEIGHT_MIN;
        }
        if (weight > RpcConstants.SERVICE_WEIGHT_MAX) {
            weight = RpcConstants.SERVICE_WEIGHT_MAX;
        }
        return weight;
    }

    /**
     * 获取serviceName
     */
    private String getServiceName(RpcService rpcService) {
        // 优先使用interfaceClass
        Class clazz = rpcService.interfaceClass();
        if (clazz == void.class) {
            return rpcService.interfaceClassName();
        }
        String serviceName = clazz.getName();
        if (serviceName == null || serviceName.trim().isEmpty()) {
            serviceName = rpcService.interfaceClassName();
        }
        return serviceName;
    }
}
