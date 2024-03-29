package com.sunny.maven.rpc.consumer.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.sunny.maven.rpc.buffer.cache.BufferCacheManager;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.consumer.common.cache.ConsumerChannelCache;
import com.sunny.maven.rpc.consumer.common.context.RpcContext;
import com.sunny.maven.rpc.exception.processor.ExceptionPostProcessor;
import com.sunny.maven.rpc.protocol.enumeration.RpcStatus;
import com.sunny.maven.rpc.protocol.enumeration.RpcType;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.proxy.api.future.RpcFuture;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import com.sunny.maven.rpc.protocol.response.RpcResponse;
import com.sunny.maven.rpc.threadpool.BufferCacheThreadPool;
import com.sunny.maven.rpc.threadpool.ConcurrentThreadPool;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUNNY
 * @description: RPC消费者处理器
 * @create: 2022-12-30 17:12
 */
@Slf4j
public class RpcConsumerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
    private volatile Channel channel;
    private SocketAddress remotePeer;

    /**
     * 存储请求ID与RpcResponse协议的映射关系
     */
//    private Map<Long, RpcProtocol<RpcResponse>> pendingResponse = new ConcurrentHashMap<>();

    private Map<Long, RpcFuture> pendingRpc = new ConcurrentHashMap<>();

    private ConcurrentThreadPool concurrentThreadPool;
    /**
     * 是否开启缓冲区
     */
    private boolean enableBuffer;
    /**
     * 缓冲区管理器
     */
    private BufferCacheManager<RpcProtocol<RpcResponse>> bufferCacheManager;
    /**
     * 异常后置处理器
     */
    private ExceptionPostProcessor exceptionPostProcessor;

    public RpcConsumerHandler(boolean enableBuffer, int bufferSize, ConcurrentThreadPool concurrentThreadPool,
                              ExceptionPostProcessor exceptionPostProcessor) {
        this.concurrentThreadPool = concurrentThreadPool;
        this.exceptionPostProcessor = exceptionPostProcessor;
        this.enableBuffer = enableBuffer;
        if (enableBuffer) {
            this.bufferCacheManager = BufferCacheManager.getInstance(bufferSize);
            BufferCacheThreadPool.submit(() -> {
                consumerBufferCache();
            });
        }
    }

    /**
     * 消费缓冲区数据
     */
    private void consumerBufferCache() {
        // 不断消息缓冲区的数据
        while (true) {
            RpcProtocol<RpcResponse> protocol = this.bufferCacheManager.take();
            if (protocol != null) {
                this.handlerResponseMessage(protocol);
            }
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemotePeer() {
        return remotePeer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remotePeer = this.channel.remoteAddress();
        ConsumerChannelCache.add(channel);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        ConsumerChannelCache.remove(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ConsumerChannelCache.remove(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("consumer IdleStateEvent...................");
            // 发送一次心跳数据
            RpcHeader header = RpcHeaderFactory.getRequestHeader(RpcConstants.SERIALIZATION_PROTOSTUFF,
                    RpcType.HEARTBEAT_FROM_CONSUMER.getType());
            RpcProtocol<RpcRequest> requestRpcProtocol = new RpcProtocol<>();
            RpcRequest request = new RpcRequest();
            request.setParameters(new Object[] {RpcConstants.HEARTBEAT_PING});
            requestRpcProtocol.setHeader(header);
            requestRpcProtocol.setBody(request);
            ctx.writeAndFlush(requestRpcProtocol);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponse> protocol)
            throws Exception {
        if (protocol == null) {
            return;
        }
        log.info("服务消费者收到的数据为====>>> {}", JSONObject.toJSONString(protocol));
        concurrentThreadPool.submit(() -> {
            this.handlerMessage(protocol, channelHandlerContext.channel());
        });
    }

    private void handlerMessage(RpcProtocol<RpcResponse> protocol, Channel channel) {
        RpcHeader header = protocol.getHeader();
        if (header.getMsgType() == RpcType.HEARTBEAT_TO_CONSUMER.getType()) {
            // 服务提供者响应的心跳消息
            this.handlerHeartbeatMessageToConsumer(protocol, header);
        } else if (header.getMsgType() == RpcType.HEARTBEAT_FROM_PROVIDER.getType()) {
            handlerHeartbeatMessageFromProvider(protocol, channel);
        } else if (header.getMsgType() == RpcType.RESPONSE.getType()) {
            // 响应消息
            this.handlerResponseMessageOrBuffer(protocol);
        }
    }

    /**
     * 包含是否开启了缓冲区的响应消息
     */
    private void handlerResponseMessageOrBuffer(RpcProtocol<RpcResponse> protocol) {
        if (enableBuffer) {
            log.info("enable buffer...");
            this.bufferCacheManager.put(protocol);
        } else {
            this.handlerResponseMessage(protocol);
        }
    }

    /**
     * 处理从服务提供者发送过来的心跳消息
     */
    private void handlerHeartbeatMessageFromProvider(RpcProtocol<RpcResponse> protocol, Channel channel) {
        RpcHeader header = protocol.getHeader();
        header.setMsgType((byte) RpcType.HEARTBEAT_TO_PROVIDER.getType());
        RpcProtocol<RpcRequest> requestRpcProtocol = new RpcProtocol<>();
        RpcRequest request = new RpcRequest();
        request.setParameters(new Object[] {RpcConstants.HEARTBEAT_PONG});
        header.setStatus((byte) RpcStatus.SUCCESS.getCode());
        requestRpcProtocol.setHeader(header);
        requestRpcProtocol.setBody(request);
        channel.writeAndFlush(requestRpcProtocol);
    }

    /**
     * 处理心跳消息
     */
    private void handlerHeartbeatMessageToConsumer(RpcProtocol<RpcResponse> protocol, RpcHeader header) {
        // 此处简单打印即可,实际场景可不做处理
        log.info("receive service provider heartbeat message, the provider is:{}, the heartbeat message is:{}",
                channel.remoteAddress(), protocol.getBody().getResult());
    }

    /**
     * 处理响应消息
     */
    private void handlerResponseMessage(RpcProtocol<RpcResponse> protocol) {
        long requestId = protocol.getHeader().getRequestId();
        RpcFuture future = pendingRpc.remove(requestId);
        if (future != null) {
            future.done(protocol);
        }
    }

    /**
     * 服务消费者向服务提供者发送请求
     * @param protocol
     */
    public RpcFuture sendRequest(RpcProtocol<RpcRequest> protocol, boolean async, boolean oneWay) {
        log.info("服务消费者发送的数据====>>> {}", JSONObject.toJSONString(protocol));
        return concurrentThreadPool.submit(() -> {
            return oneWay ? this.sendRequestOneWay(protocol) : async ?
                    this.sendRequestAsync(protocol) : this.sendRequestSync(protocol);
        });
    }

    private RpcFuture sendRequestSync(RpcProtocol<RpcRequest> protocol) {
        RpcFuture rpcFuture = this.getRpcFuture(protocol);
        channel.writeAndFlush(protocol);
        return rpcFuture;
    }

    private RpcFuture sendRequestAsync(RpcProtocol<RpcRequest> protocol) {
        RpcFuture rpcFuture = this.getRpcFuture(protocol);
        // 如果是异步调用，则将RPCFuture放入RpcContext
        RpcContext.getContext().setRpcFuture(rpcFuture);
        channel.writeAndFlush(protocol);
        return null;
    }

    private RpcFuture sendRequestOneWay(RpcProtocol<RpcRequest> protocol) {
        channel.writeAndFlush(protocol);
        return null;
    }

    private RpcFuture getRpcFuture(RpcProtocol<RpcRequest> protocol) {
        RpcFuture rpcFuture = new RpcFuture(protocol, concurrentThreadPool);
        RpcHeader header = protocol.getHeader();
        long requestId = header.getRequestId();
        pendingRpc.put(requestId, rpcFuture);
        return rpcFuture;
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        ConsumerChannelCache.remove(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        exceptionPostProcessor.postExceptionProcessor(cause);
        super.exceptionCaught(ctx, cause);
    }
}
