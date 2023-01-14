package com.sunny.maven.rpc.proxy.api.async;

import com.sunny.maven.rpc.proxy.api.future.RpcFuture;

/**
 * @author SUNNY
 * @description: 异步访问接口
 * @create: 2023-01-02 20:16
 */
public interface IAsyncObjectProxy {

    /**
     * 异步代理对象调用方法
     * @param funcName 方法名称
     * @param args 方法参数
     * @return 封装好的RPCFuture对象
     */
    RpcFuture call(String funcName, Object... args);
}
