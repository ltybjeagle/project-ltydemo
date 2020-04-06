package com.sunny.maven.rmrpc.server;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/19 14:48
 * @description：RPC服务端
 * @modified By：
 * @version: 1.0.0
 */
public abstract class RmRpcServer {
    /**
     * 监听端口
     */
    protected int port;
    /**
     * 服务启动
     */
    public abstract void start();
}
