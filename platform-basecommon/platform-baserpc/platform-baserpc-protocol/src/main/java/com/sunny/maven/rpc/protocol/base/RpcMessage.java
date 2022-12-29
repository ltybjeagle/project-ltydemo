package com.sunny.maven.rpc.protocol.base;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 消息体基础类
 * @create: 2022-12-27 17:06
 */
public class RpcMessage implements Serializable {
    /**
     * 是否单向发送
     */
    private boolean oneway;
    /**
     * 是否异步调用
     */
    private boolean async;

    public boolean isOneway() {
        return oneway;
    }

    public void setOneway(boolean oneway) {
        this.oneway = oneway;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
