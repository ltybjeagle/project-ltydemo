package com.sunny.maven.rpc.proxy.api.callback;

/**
 * @author SUNNY
 * @description: 异步回调接口
 * @create: 2022-12-31 17:32
 */
public interface AsyncRpcCallback {
    /**
     * 成功后的回调方法
     * @param result
     */
    void onSuccess(Object result);

    /**
     * 异常的回调方法
     * @param e
     */
    void onException(Exception e);
}
