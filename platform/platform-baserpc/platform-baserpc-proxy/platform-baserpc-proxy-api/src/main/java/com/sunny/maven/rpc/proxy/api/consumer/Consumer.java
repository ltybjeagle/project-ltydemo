package com.sunny.maven.rpc.proxy.api.consumer;

import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import com.sunny.maven.rpc.proxy.api.future.RpcFuture;
import com.sunny.maven.rpc.registry.api.RegistryService;

/**
 * @author SUNNY
 * @description: 服务消费者
 * @create: 2023-01-01 16:47
 */
public interface Consumer {
    /**
     * 消费者发送 request 请求
     * @param protocol
     * @param registryService
     * @return
     * @throws Exception
     */
    RpcFuture sendRequest(RpcProtocol<RpcRequest> protocol, RegistryService registryService) throws Exception;
}
