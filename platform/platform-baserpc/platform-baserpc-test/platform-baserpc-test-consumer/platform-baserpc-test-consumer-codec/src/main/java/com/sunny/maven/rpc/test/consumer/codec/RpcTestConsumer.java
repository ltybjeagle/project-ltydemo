package com.sunny.maven.rpc.test.consumer.codec;

import com.sunny.maven.rpc.flow.processor.FlowPostProcessor;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
import com.sunny.maven.rpc.test.consumer.codec.init.RpcTestConsumerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author SUNNY
 * @description: 测试消费端
 * @create: 2022-12-29 14:26
 */
public class RpcTestConsumer {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(
                    new RpcTestConsumerInitializer(ExtensionLoader.getExtension(FlowPostProcessor.class, "print")));
            bootstrap.connect("127.0.0.1", 27880).sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(2000);
            eventLoopGroup.shutdownGracefully();
        }
    }
}
