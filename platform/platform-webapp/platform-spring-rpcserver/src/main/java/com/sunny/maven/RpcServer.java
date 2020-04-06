package com.sunny.maven;

import com.sunny.maven.rmrpc.enums.RmRpcEnum;
import com.sunny.maven.rmrpc.server.RmRpcServer;
import com.sunny.maven.rmrpc.server.RmRpcServerFactory;
import com.sunny.maven.rmrpc.server.RmRpcServiceContainer;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/1 17:40
 * @description：服务启动类
 * @modified By：
 * @version: 1.0.0
 */
public class RpcServer {
    public static void main(String[] args) {
        RmRpcServerFactory serverFactory = RmRpcServerFactory.getInstance();
        serverFactory.init(RmRpcEnum.ASYNC_SERVER.getServerType(), 9998);
        RmRpcServer server = serverFactory.getRmRpcServer();
        RmRpcServiceContainer rmRpcServiceContainer = RmRpcServiceContainer.getInstance();
        rmRpcServiceContainer.init("com.sunny.maven.rpcserver");
        server.start();
    }
}
