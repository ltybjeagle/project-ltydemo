package com.sunny.maven.rpc.consumer.common.initializer;

import com.sunny.maven.rpc.codec.RpcDecoder;
import com.sunny.maven.rpc.codec.RpcEncoder;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.consumer.common.handler.RpcConsumerHandler;
import com.sunny.maven.rpc.exception.processor.ExceptionPostProcessor;
import com.sunny.maven.rpc.flow.processor.FlowPostProcessor;
import com.sunny.maven.rpc.threadpool.ConcurrentThreadPool;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: RpcConsumerInitializer
 * @create: 2022-12-30 17:27
 */
public class RpcConsumerInitializer extends ChannelInitializer<SocketChannel> {
    private int heartbeatInterval;
    private ConcurrentThreadPool concurrentThreadPool;
    private FlowPostProcessor flowPostProcessor;
    /**
     * 异常后置处理器
     */
    private ExceptionPostProcessor exceptionPostProcessor;
    private boolean enableBuffer;
    private int bufferSize;
    public RpcConsumerInitializer(int heartbeatInterval, boolean enableBuffer, int bufferSize,
                                  ConcurrentThreadPool concurrentThreadPool, FlowPostProcessor flowPostProcessor,
                                  ExceptionPostProcessor exceptionPostProcessor) {
        if (heartbeatInterval > 0) {
            this.heartbeatInterval = heartbeatInterval;
        }
        this.concurrentThreadPool = concurrentThreadPool;
        this.flowPostProcessor = flowPostProcessor;
        this.exceptionPostProcessor = exceptionPostProcessor;
        this.enableBuffer = enableBuffer;
        this.bufferSize = bufferSize;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(RpcConstants.CODEC_ENCODER, new RpcEncoder(flowPostProcessor));
        cp.addLast(RpcConstants.CODEC_DECODER, new RpcDecoder(flowPostProcessor));
        cp.addLast(RpcConstants.CODEC_CLIENT_IDLE_HANDLER,
                new IdleStateHandler(heartbeatInterval, 0, 0, TimeUnit.MILLISECONDS));
        cp.addLast(RpcConstants.CODEC_HANDLER, new RpcConsumerHandler(enableBuffer, bufferSize, concurrentThreadPool,
                exceptionPostProcessor));
    }
}
