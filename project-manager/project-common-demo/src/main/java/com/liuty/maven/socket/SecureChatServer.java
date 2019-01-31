package com.liuty.maven.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLEngine;
import java.net.InetSocketAddress;

public class SecureChatServer extends ChatServer {
    private final SslContext sslContext;
    public SecureChatServer(SslContext sslContext) {
        this.sslContext = sslContext;
    }
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group, sslContext);
    }
    public static void main(String ...args) throws Exception {
        // 支持SSL 实现https
        SelfSignedCertificate cert = new SelfSignedCertificate();
        SslContext sslContext = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();
        final SecureChatServer chatServer = new SecureChatServer(sslContext);
        ChannelFuture future = chatServer.start(new InetSocketAddress(9000));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                chatServer.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}

class SecureChatServerInitializer extends ChatServerInitializer {
    private final SslContext sslContext;
    public SecureChatServerInitializer(ChannelGroup group, SslContext sslContext) {
        super(group);
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        super.initChannel(channel);
        // 支持SSL 实现https
        SSLEngine engine = sslContext.newEngine(channel.alloc());
        engine.setUseClientMode(false);
        channel.pipeline().addFirst(new SslHandler(engine));
    }
}
