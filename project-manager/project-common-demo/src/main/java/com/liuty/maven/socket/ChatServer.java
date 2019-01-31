package com.liuty.maven.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;

public class ChatServer {
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new ChatServerInitializer(group);
    }
    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }
    public static void main(String ...args) throws Exception {
        final ChatServer chatServer = new ChatServer();
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

class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;
    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // HttpServerCodec() 等价 HttpRequestDecoder() + HttpResponseEncoder()
        pipeline.addLast(new HttpServerCodec());
//        pipeline.addLast("decoder", new HttpRequestDecoder());
//        pipeline.addLast("encoder", new HttpResponseEncoder());
        // 用于处理大数据流
        pipeline.addLast(new ChunkedWriteHandler());
        // 聚合器 将多个信息整合
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new HttpRequestHandler("/ws"));
        // 协议升级， 支持websocket
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }

    public static class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        private final String wsUri;
        private static final File INDEX;
        static {
            URL location = com.liuty.maven.socket.ChatServerInitializer.HttpRequestHandler.class
                    .getProtectionDomain().getCodeSource().getLocation();
            try {
                String path = location.toURI() + "index.html";
                path = !path.contains("file:") ? path : path.substring(5);
                INDEX = new File(path);
            } catch (URISyntaxException ex) {
                throw new IllegalStateException("Unable to locate index.html", ex);
            }
        }
        public HttpRequestHandler(String wsUri) {
            this.wsUri = wsUri;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
            if (wsUri.equalsIgnoreCase(request.uri())) {
                ctx.fireChannelRead(request.retain());
            } else {
                if (HttpUtil.is100ContinueExpected(request)) {
                    send100Continue(ctx);
                }
                RandomAccessFile file = new RandomAccessFile(INDEX, "r");
                HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
                response.headers().set("Content-Type", "text/plain; charset=UTF-8");
                boolean keepAlive = HttpUtil.isKeepAlive(request);
                if (keepAlive) {
                    response.headers().set("Content-Length", file.length());
                    response.headers().set("Connection", "keep-alive");
                }
                ctx.write(response);
                if (ctx.pipeline().get(SslHandler.class) == null) {
                    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
                } else {
                    ctx.write(new ChunkedNioFile(file.getChannel()));
                }
                ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                if (!keepAlive) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }
            }
        }

        private static void send100Continue(ChannelHandlerContext ctx) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
            ctx.writeAndFlush(response);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        private final ChannelGroup group;
        public TextWebSocketFrameHandler(ChannelGroup group) {
            this.group = group;
        }
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
                ctx.pipeline().remove(HttpRequestHandler.class);
                group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
                group.add(ctx.channel());
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            group.writeAndFlush(msg.retain());
        }
    }
}
