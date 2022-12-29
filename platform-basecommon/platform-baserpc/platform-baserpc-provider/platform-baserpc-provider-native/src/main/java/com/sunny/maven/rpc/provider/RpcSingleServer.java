package com.sunny.maven.rpc.provider;

import com.sunny.maven.rpc.common.scanner.server.RpcServiceScanner;
import com.sunny.maven.rpc.provider.common.server.base.BaseServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 以Java原生方式启动启动Rpc
 * @create: 2022-12-26 18:12
 */
@Slf4j
public class RpcSingleServer extends BaseServer {
    public RpcSingleServer(String serverAddress, String scanPackage) {
        // 调用父类构造方法
        super(serverAddress);
        try {
            this.handlerMap =
                    RpcServiceScanner.doScannerWithRpcServiceAnnotationFilterAndRegistryService(scanPackage);
        } catch (Exception e) {
            log.error("RPC Server init error : {}", e.getMessage());
        }
    }
}
