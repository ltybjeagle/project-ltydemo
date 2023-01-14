package com.sunny.maven.rpc.provider.common.server.api;

/**
 * @author SUNNY
 * @description: 启动RPC服务的接口
 * @create: 2022-12-26 17:38
 */
public interface Server {

    /**
     * 启动Netty服务
     */
    void startNettyServer();
}
