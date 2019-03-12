package com.liuty.maven.transmission;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * UDP报文消息提供者（消息生产者）
 */
public class LogEventBroadcaster {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;
    private final File file;
    public LogEventBroadcaster(InetSocketAddress address, File file) {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }
    public void run() throws Exception {
        Channel ch = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        for (;;) {
            long len = file.length();
            if (len < pointer) {
                pointer = len;
            } else if (len > pointer) {
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(pointer);
                String line;
                while ((line = raf.readLine()) != null) {
                    ch.writeAndFlush(new LogEvent(null, file.getAbsolutePath(), line, -1));
                }
                pointer = raf.getFilePointer();
                raf.close();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.interrupted();
                break;
            }
        }
    }
    public void stop() {
        eventLoopGroup.shutdownGracefully();
    }
    public static void main(String ...args) throws Exception {
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(
                new InetSocketAddress("255.255.255.255", 9000),
                new File(""));
        try {
            broadcaster.run();
        } finally {
            broadcaster.stop();
        }
    }
}
/**
 * UDP消息报文编译器
 */
class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress inetSocketAddress;
    public LogEventEncoder(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LogEvent logEvent, List<Object> list)
            throws Exception {
        byte[] file = logEvent.getLogFile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = channelHandlerContext.alloc().buffer(file.length + msg.length + 1);
        buf.writeBytes(file);
        buf.writeByte(LogEvent.SEPARATOR);
        buf.writeBytes(msg);
        list.add(new DatagramPacket(buf, inetSocketAddress));
    }
}
/**
 * UDP消息体（POJO）
 */
final class LogEvent {
    public static final byte SEPARATOR = (byte) ':';
    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long received;
    public LogEvent(String logFile, String msg) {
        this(null, logFile, msg, -1);
    }
    public LogEvent(InetSocketAddress source, String logFile, String msg, long received) {
        this.source = source;
        this.logFile = logFile;
        this.msg = msg;
        this.received = received;
    }
    public InetSocketAddress getSource() {
        return source;
    }
    public String getLogFile() {
        return logFile;
    }
    public String getMsg() {
        return msg;
    }
    public long getReceived() {
        return received;
    }
}
