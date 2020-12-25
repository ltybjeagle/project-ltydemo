package com.sunny.maven.rmrpc.client;

import com.sunny.maven.rmrpc.client.nio.async.RmRpcAsyncClient;
import com.sunny.maven.rmrpc.client.nio.sync.RmRpcSyncClient;
import com.sunny.maven.rmrpc.enums.RmRpcEnum;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/24 20:48
 * @description：服务消费端工厂
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcClientFactory {
    private static final Lock lock = new ReentrantLock();
    private String clientEnum;
    private String host;
    private int port;

    public RmRpcClient getRmRpcClient() {
        if (RmRpcEnum.SYNC_SERVER.getServerType().equalsIgnoreCase(clientEnum)) {
            return new RmRpcSyncClient(host, port);
        }
        if (RmRpcEnum.ASYNC_SERVER.getServerType().equalsIgnoreCase(clientEnum)) {
            return new RmRpcAsyncClient(host, port);
        }
        return null;
    }

    public void init(String host, int port, String clientEnum) {
        this.host = host;
        this.port = port;
        this.clientEnum = clientEnum;
    }

    public static final RmRpcClientFactory getInstance() {
        return RmRpcClientFactoryHandler.rmRpcClientFactory;
    }

    private static class RmRpcClientFactoryHandler {
        private static final RmRpcClientFactory rmRpcClientFactory = new RmRpcClientFactory();
    }
    private RmRpcClientFactory() {
    }
}
