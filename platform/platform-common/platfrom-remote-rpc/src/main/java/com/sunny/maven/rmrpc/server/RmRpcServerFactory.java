package com.sunny.maven.rmrpc.server;

import com.sunny.maven.rmrpc.enums.RmRpcEnum;
import com.sunny.maven.rmrpc.server.nio.async.RmRpcAsyncServer;
import com.sunny.maven.rmrpc.server.nio.sync.RmRpcSyncServer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/19 15:12
 * @description：RPC服务工厂
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcServerFactory {
    private static final Lock lock = new ReentrantLock();
    private RmRpcServer rmRpcServer = null;
    public RmRpcServer getRmRpcServer() {
        return rmRpcServer;
    }

    /**
     * 服务初始化
     * @param serverEnum
     * @param port
     */
    public void init(String serverEnum, int port) {
        if (rmRpcServer == null) {
            try {
                lock.lock();
                if (rmRpcServer == null) {
                    if (RmRpcEnum.SYNC_SERVER.getServerType().equalsIgnoreCase(serverEnum)) {
                        rmRpcServer = new RmRpcSyncServer(port);
                    }
                    if (RmRpcEnum.ASYNC_SERVER.getServerType().equalsIgnoreCase(serverEnum)) {
                        rmRpcServer = new RmRpcAsyncServer(port);
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static final RmRpcServerFactory getInstance() {
        return RmRpcServerFactoryHandler.rmRpcServerFactory;
    }

    private static class RmRpcServerFactoryHandler {
        private static final RmRpcServerFactory rmRpcServerFactory = new RmRpcServerFactory();
    }

    private RmRpcServerFactory() {
    }
}
