package com.sunny.maven.rmrpc.client;

import com.sunny.maven.rmrpc.common.RmRpcRequest;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/24 19:38
 * @description：服务消费端
 * @modified By：
 * @version: 1.0.0
 */
public abstract class RmRpcClient {
    protected String host;
    protected int port;
    protected RmRpcRequest request;

    /**
     * 服务消费方法
     * @return
     * @throws Throwable
     */
    public abstract Object start() throws Throwable;

    /**
     * 消费信息设置
     * @param request
     */
    protected void setRequest(RmRpcRequest request) {
        this.request = request;
    }
}
