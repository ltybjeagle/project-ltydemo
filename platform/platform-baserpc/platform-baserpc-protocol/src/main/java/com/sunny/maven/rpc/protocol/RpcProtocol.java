package com.sunny.maven.rpc.protocol;

import com.sunny.maven.rpc.protocol.header.RpcHeader;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: Rpc协议
 * @create: 2022-12-27 17:39
 */
public class RpcProtocol<T> implements Serializable {
    private static final long serialVersionUID = 5862082668748514251L;
    /**
     * 消息头
     */
    private RpcHeader header;
    /**
     * 消息体
     */
    private T body;

    public RpcHeader getHeader() {
        return header;
    }

    public void setHeader(RpcHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
