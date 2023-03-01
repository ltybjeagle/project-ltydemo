package com.sunny.maven.rpc.provider.common.server.base;

import com.sunny.maven.rpc.codec.RpcDecoder;
import com.sunny.maven.rpc.codec.RpcEncoder;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.provider.common.handler.RpcProviderHandler;
import com.sunny.maven.rpc.provider.common.manager.ProviderConnectionManager;
import com.sunny.maven.rpc.provider.common.server.api.Server;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.registry.zookeeper.ZookeeperRegistryService;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
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
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    protected String serverRegistryHost;
    protected int serverRegistryPort;
    /**
     * 存储的是实体类关系
     */
    protected Map<String, Object> handlerMap = new HashMap<>();
    private String reflectType;

    protected RegistryService registryService;
    /**
     * 心跳定时任务线程池
     */
    private ScheduledExecutorService executorService;
    /**
     * 心跳间隔时间，默认30秒
     */
    private int heartbeatInterval = 30000;
    /**
     * 扫描并移除空闲连接时间，默认60秒
     */
    private int scanNotActiveChannelInterval = 60000;
    /**
     * 结果缓存过期时长，默认5秒
     */
    private int resultCacheExpire = 5000;
    /**
     * 是否开启结果缓存
     */
    private boolean enableResultCache;
    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maximumPoolSize;

    public BaseServer(String serverAddress, String serverRegistryAddress, String reflectType, String registryAddress,
                      String registryType, String registryLoadBalanceType, int heartbeatInterval,
                      int scanNotActiveChannelInterval, boolean enableResultCache, int resultCacheExpire,
                      int corePoolSize, int maximumPoolSize) {
        if (StringUtils.isNotEmpty(serverAddress)) {
            String[] serverArray = serverAddress.split(":");
            host = serverArray[0];
            port = Integer.parseInt(serverArray[1]);
        }
        if (StringUtils.isNotEmpty(serverRegistryAddress)) {
            String[] serverRegistryAddressArray = serverRegistryAddress.split(":");
            this.serverRegistryHost = serverRegistryAddressArray[0];
            this.serverRegistryPort = Integer.parseInt(serverRegistryAddressArray[1]);
        } else {
            this.serverRegistryHost = host;
            this.serverRegistryPort = port;
        }
        if (heartbeatInterval > 0) {
            this.heartbeatInterval = heartbeatInterval;
        }
        if (scanNotActiveChannelInterval > 0) {
            this.scanNotActiveChannelInterval = scanNotActiveChannelInterval;
        }
        this.reflectType = reflectType;
        this.registryService = this.getRegistryService(registryAddress, registryType, registryLoadBalanceType);
        if (resultCacheExpire > 0) {
            this.resultCacheExpire = resultCacheExpire;
        }
        this.enableResultCache = enableResultCache;
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
    }

    private RegistryService getRegistryService(String registryAddress, String registryType,
                                               String registryLoadBalanceType) {
        RegistryService registryService = null;
        try {
            registryService = ExtensionLoader.getExtension(RegistryService.class, registryType);
            registryService.init(new RegistryConfig(registryAddress, registryType, registryLoadBalanceType));
        } catch (Exception e) {
            log.error("RPC Server init error", e);
        }
        return registryService;
    }

    @Override
    public void startNettyServer() {
        this.startHeartBeat();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().
                                    addLast(RpcConstants.CODEC_DECODER, new RpcDecoder()).
                                    addLast(RpcConstants.CODEC_ENCODER, new RpcEncoder()).
                                    addLast(RpcConstants.CODEC_SERVER_IDLE_HANDLER,
                                            new IdleStateHandler(0, 0,
                                                    heartbeatInterval, TimeUnit.MILLISECONDS)).
                                    addLast(RpcConstants.CODEC_HANDLER,
                                            new RpcProviderHandler(reflectType, enableResultCache, resultCacheExpire,
                                                    corePoolSize, maximumPoolSize, handlerMap));
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

    private void startHeartBeat() {
        executorService = Executors.newScheduledThreadPool(2);
        // 扫描并处理所有不活跃的连接
        executorService.scheduleAtFixedRate(() -> {
            log.info("=============scanNotActiveChannel============");
            ProviderConnectionManager.scanNotActiveChannel();
        }, 10, scanNotActiveChannelInterval, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(() -> {
            log.info("=============broadcastPingMessageFromProvider============");
            ProviderConnectionManager.broadcastPingMessageFromProvider();
        }, 3, heartbeatInterval, TimeUnit.MILLISECONDS);
    }
}
