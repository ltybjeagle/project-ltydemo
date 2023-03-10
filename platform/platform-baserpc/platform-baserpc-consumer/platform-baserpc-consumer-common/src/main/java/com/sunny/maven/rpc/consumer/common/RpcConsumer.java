package com.sunny.maven.rpc.consumer.common;

import com.sunny.maven.rpc.common.exception.RpcException;
import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.common.ip.IpUtils;
import com.sunny.maven.rpc.common.threadpool.ClientThreadPool;
import com.sunny.maven.rpc.common.utils.StringUtils;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.consumer.common.helper.RpcConsumerHandlerHelper;
import com.sunny.maven.rpc.consumer.common.manager.ConsumerConnectionManager;
import com.sunny.maven.rpc.flow.processor.FlowPostProcessor;
import com.sunny.maven.rpc.loadbalancer.context.ConnectionsContext;
import com.sunny.maven.rpc.protocol.meta.ServiceMeta;
import com.sunny.maven.rpc.proxy.api.consumer.Consumer;
import com.sunny.maven.rpc.proxy.api.future.RpcFuture;
import com.sunny.maven.rpc.consumer.common.handler.RpcConsumerHandler;
import com.sunny.maven.rpc.consumer.common.initializer.RpcConsumerInitializer;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
import com.sunny.maven.rpc.threadpool.ConcurrentThreadPool;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 服务消费者
 * @create: 2022-12-30 17:28
 */
@Slf4j
public class RpcConsumer implements Consumer {

    private Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    private final String localIp;
    private static volatile RpcConsumer instance;
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
     * 重试间隔时间
     */
    private int retryInterval = 1000;
    /**
     * 重试次数
     */
    private int retryTimes = 3;
    /**
     * 当前重试次数
     */
    private volatile int currentConnectRetryTimes = 0;
    /**
     * 是否开启直连服务
     */
    private boolean enableDirectServer = false;
    /**
     * 直连服务的地址
     */
    private String directServerUrl;
    /**
     * 是否开启延迟连接
     */
    private boolean enableDelayConnection = false;
    /**
     * 未开启延迟连接时，是否已经初始化连接
     */
    private volatile boolean initConnection = false;
    /**
     * 并发处理线程池
     */
    private ConcurrentThreadPool concurrentThreadPool;
    /**
     * 流控分析后置处理器
     */
    private FlowPostProcessor flowPostProcessor;
    /**
     * 是否开启数据缓冲
     */
    private boolean enableBuffer;
    /**
     * 缓冲区大小
     */
    private int bufferSize;

    private RpcConsumer() {
        localIp = IpUtils.getLocalHostIp();
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(4);
    }

    public RpcConsumer setEnableDirectServer(boolean enableDirectServer) {
        this.enableDirectServer = enableDirectServer;
        return this;
    }

    public RpcConsumer setDirectServerUrl(String directServerUrl) {
        this.directServerUrl = directServerUrl;
        return this;
    }

    public RpcConsumer setHeartbeatInterval(int heartbeatInterval) {
        if (heartbeatInterval > 0) {
            this.heartbeatInterval = heartbeatInterval;
        }
        return this;
    }

    public RpcConsumer setScanNotActiveChannelInterval(int scanNotActiveChannelInterval) {
        if (scanNotActiveChannelInterval > 0) {
            this.scanNotActiveChannelInterval = scanNotActiveChannelInterval;
        }
        return this;
    }

    public RpcConsumer setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval <= 0 ? RpcConstants.DEFAULT_RETRY_INTERVAL : retryInterval;
        return this;
    }

    public RpcConsumer setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes <= 0 ? RpcConstants.DEFAULT_RETRY_TIMES : retryTimes;
        return this;
    }

    public RpcConsumer setEnableDelayConnection(boolean enableDelayConnection) {
        this.enableDelayConnection = enableDelayConnection;
        return this;
    }

    public RpcConsumer setConcurrentThreadPool(ConcurrentThreadPool concurrentThreadPool) {
        this.concurrentThreadPool = concurrentThreadPool;
        return this;
    }

    public RpcConsumer setFlowPostProcessor(String flowType) {
        if (StringUtils.isEmpty(flowType)) {
            flowType = RpcConstants.FLOW_POST_PROCESSOR_PRINT;
        }
        this.flowPostProcessor = ExtensionLoader.getExtension(FlowPostProcessor.class, flowType);
        return this;
    }

    public RpcConsumer setEnableBuffer(boolean enableBuffer) {
        this.enableBuffer = enableBuffer;
        return this;
    }

    public RpcConsumer setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }


    public RpcConsumer buildNettyGroup() {
        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(
                    new RpcConsumerInitializer(heartbeatInterval, enableBuffer, bufferSize, concurrentThreadPool,
                            flowPostProcessor));
        } catch (IllegalStateException e) {
            bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(
                    new RpcConsumerInitializer(heartbeatInterval, enableBuffer, bufferSize, concurrentThreadPool,
                            flowPostProcessor));
        }
        return this;
    }

    /**
     * 初始化连接
     */
    public RpcConsumer buildConnection(RegistryService registryService) {
        // 未开启延迟连接，并且未初始化连接
        if (!enableDelayConnection && !initConnection) {
            this.initConnection(registryService);
            this.initConnection = true;
        }
        // TODO 启动心跳，后续优化
        this.startHeartBeat();
        return this;
    }

    private void startHeartBeat() {
        executorService = Executors.newScheduledThreadPool(2);
        // 扫描并处理所有不活跃的连接
        executorService.scheduleAtFixedRate(() -> {
            log.info("=============scanNotActiveChannel============");
            ConsumerConnectionManager.scanNotActiveChannel();
        }, 10, scanNotActiveChannelInterval, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(() -> {
            log.info("=============broadcastPingMessageFromConsumer============");
            ConsumerConnectionManager.broadcastPingMessageFromConsumer();
        }, 3, heartbeatInterval, TimeUnit.MILLISECONDS);
    }

    public static RpcConsumer getInstance() {
        if (instance == null) {
            synchronized (RpcConsumer.class) {
                if (instance == null) {
                    instance = new RpcConsumer();
                }
            }
        }
        return instance;
    }

    public void close() {
        RpcConsumerHandlerHelper.closeRpcClientHandler();
        eventLoopGroup.shutdownGracefully();
        ClientThreadPool.shutdown();
        executorService.shutdown();
    }

    @Override
    public RpcFuture sendRequest(RpcProtocol<RpcRequest> protocol, RegistryService registryService) throws Exception {
        RpcRequest request = protocol.getBody();
        String serviceKsy = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getVersion(),
                request.getGroup());
        Object[] params = request.getParameters();
        int invokerHashCode = (params == null || params.length <= 0) ? serviceKsy.hashCode() : params[0].hashCode();
        // 获取发送消息的handler
        RpcConsumerHandler handler = getRpcConsumerHandlerWithRetry(registryService, serviceKsy, invokerHashCode);
        RpcFuture future = null;
        if (handler != null) {
            future = handler.sendRequest(protocol, request.isAsync(), request.isOneway());
        }
        return future;
    }

    /**
     * 基于重试获取发送消息的handler
     */
    private RpcConsumerHandler getRpcConsumerHandlerWithRetry(RegistryService registryService, String serviceKsy,
                                                              int invokerHashCode) throws Exception {
        log.info("获取服务消费者处理器...");
        RpcConsumerHandler handler = this.getRpcConsumerHandler(registryService, serviceKsy, invokerHashCode);
        // 获取的handler为空，启动重试机制
        if (handler == null) {
            for (int i = 1; i <= retryTimes; i++) {
                log.info("获取服务消费者处理器第【{}】次重试...", i);
                handler = this.getRpcConsumerHandler(registryService, serviceKsy, invokerHashCode);
                if (handler != null) {
                    break;
                }
                Thread.sleep(retryInterval);
            }
        }
        return handler;
    }

    /**
     * 获取发送消息的handler
     */
    private RpcConsumerHandler getRpcConsumerHandler(RegistryService registryService, String serviceKsy,
                                                     int invokerHashCode) throws Exception {
        ServiceMeta serviceMeta = this.getDirectServiceMetaOrWithRetry(registryService, serviceKsy, invokerHashCode);
        RpcConsumerHandler handler = null;
        if (serviceMeta != null) {
            handler = getRpcConsumerHandlerWithRetry(serviceMeta);
        }
        return handler;
    }

    /**
     * 直连服务提供者或者结合重试获取服务元数据信息
     */
    private ServiceMeta getDirectServiceMetaOrWithRetry(RegistryService registryService, String serviceKsy,
                                                int invokerHashCode) throws Exception {
        ServiceMeta serviceMeta = null;
        if (enableDirectServer) {
            serviceMeta = this.getServiceMeta(directServerUrl, registryService, invokerHashCode);
        } else {
            serviceMeta = this.getServiceMetaWithRetry(registryService, serviceKsy, invokerHashCode);
        }
        return serviceMeta;
    }

    /**
     * 直连服务提供者
     */
    private ServiceMeta getServiceMeta(String directServerUrl, RegistryService registryService, int invokerHashCode) {
        log.info("服务消费者直连服务提供者...");
        // 只配置了一个服务提供者地址
        if (!directServerUrl.contains(RpcConstants.RPC_MULTI_DIRECT_SERVERS_SEPARATOR)) {
            return getDirectServiceMetaWithCheck(directServerUrl);
        }
        // 配置了多个服务提供者地址
        return registryService.select(getMultiServiceMeta(directServerUrl), invokerHashCode, localIp);
    }

    /**
     * 获取多个服务提供者元数据
     */
    private List<ServiceMeta> getMultiServiceMeta(String directServerUrl) {
        List<ServiceMeta> serviceMetaList = new ArrayList<>();
        String[] directServerUrlArray = directServerUrl.split(RpcConstants.RPC_MULTI_DIRECT_SERVERS_SEPARATOR);
        if (directServerUrlArray != null && directServerUrlArray.length > 0) {
            for (String directUrl : directServerUrlArray) {
                serviceMetaList.add(getDirectServiceMeta(directUrl));
            }
        }
        return serviceMetaList;
    }

    /**
     * 服务消费者直连服务提供者
     */
    private ServiceMeta getDirectServiceMetaWithCheck(String directServerUrl) {
        if (StringUtils.isEmpty(directServerUrl)) {
            throw new RpcException("direct server url is null ...");
        }
        if (!directServerUrl.contains(RpcConstants.IP_PORT_SPLIT)) {
            throw new RpcException("direct server url not contains : ");
        }
        return this.getDirectServiceMeta(directServerUrl);
    }

    /**
     * 获取直连服务提供者元数据
     */
    private ServiceMeta getDirectServiceMeta(String directServerUrl) {
        ServiceMeta serviceMeta = new ServiceMeta();
        String[] directServerUrlArray = directServerUrl.split(RpcConstants.IP_PORT_SPLIT);
        serviceMeta.setServiceAddr(directServerUrlArray[0]);
        serviceMeta.setServicePort(Integer.parseInt(directServerUrlArray[1]));
        return serviceMeta;
    }

    /**
     * 重试获取服务提供者元数据
     */
    private ServiceMeta getServiceMetaWithRetry(RegistryService registryService, String serviceKsy, int invokerHashCode)
            throws Exception {
        // 首次获取服务元数据信息，如果获取到，则直接返回，否则进行重试
        log.info("获取服务提供者元数据...");
        ServiceMeta serviceMeta = registryService.discovery(serviceKsy, invokerHashCode, localIp);
        // 启动重试机制
        if (serviceMeta == null) {
            for (int i = 1; i <= retryTimes; i++) {
                log.info("获取服务提供者元数据第【{}】次重试...", i);
                serviceMeta = registryService.discovery(serviceKsy, invokerHashCode, localIp);
                if (serviceMeta != null) {
                    break;
                }
                Thread.sleep(retryInterval);
            }
        }
        return serviceMeta;
    }

    /**
     * 获取RpcConsumerHandler
     */
    private RpcConsumerHandler getRpcConsumerHandlerWithRetry(ServiceMeta serviceMeta) throws InterruptedException {
        log.info("服务消费者连接服务提供者...");
        RpcConsumerHandler handler = null;
        try {
            handler = this.getRpcConsumerHandlerWithCache(serviceMeta);
        } catch (Exception e) {
            // 连接异常
            if (e instanceof ConnectException) {
                // 启动重试机制
                if (handler == null) {
                    if (currentConnectRetryTimes < retryTimes) {
                        currentConnectRetryTimes++;
                        log.info("服务消费者连接服务提供者第【{}】次重试...", currentConnectRetryTimes);
                        handler = this.getRpcConsumerHandlerWithRetry(serviceMeta);
                        Thread.sleep(retryInterval);
                    }
                }
            }
        }
        return handler;
    }

    /**
     * 从缓存中获取RpcConsumerHandler，缓存中没有，再创建
     */
    private RpcConsumerHandler getRpcConsumerHandlerWithCache(ServiceMeta serviceMeta) throws InterruptedException {
        RpcConsumerHandler handler = RpcConsumerHandlerHelper.get(serviceMeta);
        // 缓存中无RpcClientHandler
        if (handler == null) {
            handler = getRpcConsumerHandler(serviceMeta);
            RpcConsumerHandlerHelper.put(serviceMeta, handler);
        } else if (!handler.getChannel().isActive()) {
            // 缓存中存在RpcClientHandler，但不活跃
            handler.close();
            handler = getRpcConsumerHandler(serviceMeta);
            RpcConsumerHandlerHelper.put(serviceMeta, handler);
        }
        return handler;
    }

    /**
     * 创建连接并返回RpcClientHandler
     */
    private RpcConsumerHandler getRpcConsumerHandler(ServiceMeta serviceMeta) throws InterruptedException {
        ChannelFuture channelFuture = bootstrap.connect(serviceMeta.getServiceAddr(),
                serviceMeta.getServicePort()).sync();
        channelFuture.addListener((ChannelFutureListener) listener -> {
            if (channelFuture.isSuccess()) {
                log.info("connect rpc server {} on port {} success.",
                        serviceMeta.getServiceAddr(), serviceMeta.getServicePort());
                // 添加连接信息，在服务消费者端记录每个服务提供者实例的连接次数
                ConnectionsContext.add(serviceMeta);
                // 连接成功，将当前连接重试次数设置为0
                currentConnectRetryTimes = 0;
            } else {
                log.error("connect rpc server {} on port {} failed.",
                        serviceMeta.getServiceAddr(), serviceMeta.getServicePort());
                channelFuture.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        return channelFuture.channel().pipeline().get(RpcConsumerHandler.class);
    }

    /**
     * 初始化连接
     */
    private void initConnection(RegistryService registryService) {
        List<ServiceMeta> serviceMetaList = new ArrayList<>();
        try {
            if (enableDirectServer) {
                if (!directServerUrl.contains(RpcConstants.RPC_MULTI_DIRECT_SERVERS_SEPARATOR)) {
                    serviceMetaList.add(this.getDirectServiceMetaWithCheck(directServerUrl));
                } else {
                    serviceMetaList.addAll(this.getMultiServiceMeta(directServerUrl));
                }
            } else {
                serviceMetaList.addAll(registryService.discoveryAll());
            }
        } catch (Exception e) {
            log.error("init connection throws exception, the message is: {}", e.getMessage());
        }
        for (ServiceMeta serviceMeta : serviceMetaList) {
            RpcConsumerHandler handler = null;
            try {
                handler = this.getRpcConsumerHandler(serviceMeta);
            } catch (InterruptedException e) {
                log.error("call getRpcConsumerHandler() method throws InterruptedException, the message is: {}",
                        e.getMessage());
                continue;
            }
            RpcConsumerHandlerHelper.put(serviceMeta, handler);
        }
    }
}
