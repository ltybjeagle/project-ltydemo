package com.sunny.maven.rpc.provider.common.server.base;

import com.sunny.maven.rpc.codec.RpcDecoder;
import com.sunny.maven.rpc.codec.RpcEncoder;
import com.sunny.maven.rpc.provider.common.handler.RpcProviderHandler;
import com.sunny.maven.rpc.provider.common.server.api.Server;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.registry.zookeeper.ZookeeperRegistryService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 基础服务
 * @create: 2022-12-26 17:42
 */
@Slf4j
public class BaseServer implements Server {
    /**
     * 主机域名或者IP地址
     */
    protected String host = "127.0.0.1";
    /**
     * 端口号
     */
    protected int port = 27110;
    /**
     * 存储的是实体类关系
     */
    protected Map<String, Object> handlerMap = new HashMap<>();
    private String reflectType;

    protected RegistryService registryService;

    public BaseServer(String serverAddress, String reflectType, String registryAddress, String registryType,
                      String registryLoadBalanceType) {
        if (StringUtils.isNotEmpty(serverAddress)) {
            String[] serverArray = serverAddress.split(":");
            host = serverArray[0];
            port = Integer.parseInt(serverArray[1]);
        }
        this.reflectType = reflectType;
        this.registryService = this.getRegistryService(registryAddress, registryType, registryLoadBalanceType);
    }

    private RegistryService getRegistryService(String registryAddress, String registryType,
                                               String registryLoadBalanceType) {
        //TODO 后续扩展支持SPI
        RegistryService registryService = null;
        try {
            registryService = new ZookeeperRegistryService();
            registryService.init(new RegistryConfig(registryAddress, registryType, registryLoadBalanceType));
        } catch (Exception e) {
            log.error("RPC Server init error", e);
        }
        return registryService;
    }

    @Override
    public void startNettyServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().
                                    addLast(new RpcDecoder()).
                                    addLast(new RpcEncoder()).
                                    addLast(new RpcProviderHandler(reflectType, handlerMap));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128).
                    childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(host, port).sync();
            log.info("Server started on {}:{}", host, port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("RPC Server start error : {}", e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
