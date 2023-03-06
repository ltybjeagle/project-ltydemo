package com.sunny.maven.rpc.buffer.object;

import com.sunny.maven.rpc.protocol.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 缓冲对象
 * @create: 2023-03-04 14:40
 */
public class BufferObject<T> implements Serializable {
    private static final long serialVersionUID = 1496814964612918080L;
    /**
     * Netty读写数据的ChannelHandlerContext
     */
    private ChannelHandlerContext ctx;
    /**
     * 网络传输协议对象
     */
    private RpcProtocol<T> protocol;

    public BufferObject() {
    }

    public BufferObject(ChannelHandlerContext ctx, RpcProtocol<T> protocol) {
        this.ctx = ctx;
        this.protocol = protocol;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public RpcProtocol<T> getProtocol() {
        return protocol;
    }

    public void setProtocol(RpcProtocol<T> protocol) {
        this.protocol = protocol;
    }
}
