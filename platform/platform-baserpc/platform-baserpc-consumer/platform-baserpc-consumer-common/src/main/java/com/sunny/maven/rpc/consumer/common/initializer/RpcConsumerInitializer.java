package com.sunny.maven.rpc.consumer.common.initializer;

import com.sunny.maven.rpc.codec.RpcDecoder;
import com.sunny.maven.rpc.codec.RpcEncoder;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.consumer.common.handler.RpcConsumerHandler;
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
    public RpcConsumerInitializer(int heartbeatInterval) {
        if (heartbeatInterval > 0) {
            this.heartbeatInterval = heartbeatInterval;
        }
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(RpcConstants.CODEC_ENCODER, new RpcEncoder());
        cp.addLast(RpcConstants.CODEC_DECODER, new RpcDecoder());
        cp.addLast(RpcConstants.CODEC_CLIENT_IDLE_HANDLER,
                new IdleStateHandler(heartbeatInterval, 0, 0, TimeUnit.MILLISECONDS));
        cp.addLast(RpcConstants.CODEC_HANDLER, new RpcConsumerHandler());
    }
}
