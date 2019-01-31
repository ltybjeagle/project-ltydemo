package com.liuty.maven.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * UDP消息监听器（消息消费者）
 */
public class LogEventMonitor {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;
    public LogEventMonitor(InetSocketAddress address) {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LogEventDecoder());
                        pipeline.addLast(new LogEventHandler());
                    }
                }).localAddress(address);
    }
    public Channel bind() {
        return bootstrap.bind().syncUninterruptibly().channel();
    }
    public void stop() {
        eventLoopGroup.shutdownGracefully();
    }
    public static void main(String ...args) throws Exception {
        LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(9000));
        try {
            Channel channel = monitor.bind();
            System.out.println("LogEventMonitor running");
            channel.closeFuture().sync();
        } finally {
            monitor.stop();
        }
    }
}
/**
 * UDP报文解析器
 */
class LogEventDecoder extends MessageToMessageEncoder<DatagramPacket> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket
            , List<Object> list) throws Exception {
        ByteBuf data = datagramPacket.content();
        int idx = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);
        String fileName = data.slice(0, idx).toString(CharsetUtil.UTF_8);
        String msg = data.slice(idx + 1, data.readableBytes()).toString(CharsetUtil.UTF_8);
        list.add(new LogEvent(datagramPacket.sender(), fileName, msg, System.currentTimeMillis()));
    }
}
/**
 * UDP报文处理器
 */
class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent)
            throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(logEvent.getReceived());
        builder.append(" [");
        builder.append(logEvent.getSource());
        builder.append("] [");
        builder.append(logEvent.getLogFile());
        builder.append("] : ");
        builder.append(logEvent.getMsg());
        System.out.println(builder.toString());
    }
}
