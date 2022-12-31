package com.sunny.maven.rpc.consumer.common;

import com.sunny.maven.rpc.common.threadpool.ClientThreadPool;
import com.sunny.maven.rpc.consumer.common.future.RpcFuture;
import com.sunny.maven.rpc.consumer.common.handler.RpcConsumerHandler;
import com.sunny.maven.rpc.consumer.common.initializer.RpcConsumerInitializer;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUNNY
 * @description: 服务消费者
 * @create: 2022-12-30 17:28
 */
@Slf4j
public class RpcConsumer {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    private static volatile RpcConsumer instance;

    private static Map<String, RpcConsumerHandler> handlerMap = new ConcurrentHashMap<>();

    private RpcConsumer() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(4);
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new RpcConsumerInitializer());
    }

    public static RpcConsumer getInstance() {
        if (instance == null) {
            synchronized (RpcConsumer.class) {
                if (instance == null) {
                    instance = new RpcConsumer();
                }
            }
        }
        return instance;
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
        ClientThreadPool.shutdown();
    }

    public RpcFuture sendRequest(RpcProtocol<RpcRequest> protocol) throws Exception {
        // TODO 暂时写死，后续在引入注册中心时，从注册中心获取
        String serviceAddress = "127.0.0.1";
        int port = 27880;
        String key = serviceAddress.concat("_").concat(String.valueOf(port));
        RpcConsumerHandler handler = handlerMap.get(key);
        // 缓存中无RpcClientHandler
        if (handler == null) {
            handler = getRpcConsumerHandler(serviceAddress, port);
            handlerMap.put(key, handler);
        } else if (!handler.getChannel().isActive()) {
            // 缓存中存在RpcClientHandler，但不活跃
            handler.close();
            handler = getRpcConsumerHandler(serviceAddress, port);
            handlerMap.put(key, handler);
        }
        RpcRequest request = protocol.getBody();
        return handler.sendRequest(protocol, request.isAsync(), request.isOneway());
    }

    /**
     * 创建连接并返回RpcClientHandler
     * @param serviceAddress
     * @param port
     * @return
     * @throws InterruptedException
     */
    private RpcConsumerHandler getRpcConsumerHandler(String serviceAddress, int port) throws InterruptedException {
        ChannelFuture channelFuture = bootstrap.connect(serviceAddress, port).sync();
        channelFuture.addListener((ChannelFutureListener) listener -> {
            if (channelFuture.isSuccess()) {
                log.info("connect rpc server {} on port {} success.", serviceAddress, port);
            } else {
                log.error("connect rpc server {} on port {} failed.", serviceAddress, port);
                channelFuture.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        return channelFuture.channel().pipeline().get(RpcConsumerHandler.class);
    }
}
