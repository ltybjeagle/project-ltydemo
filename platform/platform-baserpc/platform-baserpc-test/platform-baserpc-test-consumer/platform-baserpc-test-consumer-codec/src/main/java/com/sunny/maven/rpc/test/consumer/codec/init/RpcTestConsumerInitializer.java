package com.sunny.maven.rpc.test.consumer.codec.init;

import com.sunny.maven.rpc.codec.RpcDecoder;
import com.sunny.maven.rpc.codec.RpcEncoder;
import com.sunny.maven.rpc.flow.processor.FlowPostProcessor;
import com.sunny.maven.rpc.test.consumer.codec.handler.RpcTestConsumerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SUNNY
 * @description: Netty初始化
 * @create: 2022-12-29 14:21
 */
public class RpcTestConsumerInitializer extends ChannelInitializer<SocketChannel> {
    private FlowPostProcessor flowPostProcessor;

    public RpcTestConsumerInitializer(FlowPostProcessor flowPostProcessor){
        this.flowPostProcessor = flowPostProcessor;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new RpcEncoder(flowPostProcessor));
        cp.addLast(new RpcDecoder(flowPostProcessor));
        cp.addLast(new RpcTestConsumerHandler());
    }
}
