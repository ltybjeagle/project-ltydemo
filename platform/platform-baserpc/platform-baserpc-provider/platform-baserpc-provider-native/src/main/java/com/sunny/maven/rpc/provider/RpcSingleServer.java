package com.sunny.maven.rpc.provider;

import com.sunny.maven.rpc.provider.common.scanner.RpcServiceScanner;
import com.sunny.maven.rpc.provider.common.server.base.BaseServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 以Java原生方式启动启动Rpc
 * @create: 2022-12-26 18:12
 */
@Slf4j
public class RpcSingleServer extends BaseServer {
    public RpcSingleServer(String serverAddress, String serverRegistryAddress, String scanPackage, String reflectType,
                           String registryAddress, String registryType, String registryLoadBalanceType,
                           int heartbeatInterval, int scanNotActiveChannelInterval, boolean enableResultCache,
                           int resultCacheExpire, int corePoolSize, int maximumPoolSize, String flowType,
                           int maxConnections, String disuseStrategyType) {
        // 调用父类构造方法
        super(serverAddress, serverRegistryAddress, reflectType, registryAddress, registryType, registryLoadBalanceType,
                heartbeatInterval, scanNotActiveChannelInterval, enableResultCache, resultCacheExpire, corePoolSize,
                maximumPoolSize, flowType, maxConnections, disuseStrategyType);
        try {
            this.handlerMap =
                    RpcServiceScanner.doScannerWithRpcServiceAnnotationFilterAndRegistryService(this.serverRegistryHost,
                            this.serverRegistryPort, scanPackage, this.registryService);
        } catch (Exception e) {
            log.error("RPC Server init error : {}", e.getMessage());
        }
    }
}
