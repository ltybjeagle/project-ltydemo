package com.sunny.maven.rpc.consumer;

import com.sunny.maven.rpc.common.exception.RegistryException;
import com.sunny.maven.rpc.consumer.common.RpcConsumer;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;
import com.sunny.maven.rpc.proxy.api.async.IAsyncObjectProxy;
import com.sunny.maven.rpc.proxy.api.config.ProxyConfig;
import com.sunny.maven.rpc.proxy.api.object.ObjectProxy;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author SUNNY
 * @description: 服务消费客户端
 * @create: 2023-01-01 22:34
 */
@Slf4j
public class RpcClient {
    /**
     * 服务版本号
     */
    private String serviceVersion;
    /**
     * 服务分组
     */
    private String serviceGroup;
    /**
     * 序列化类型
     */
    private String serializationType;
    /**
     * 超时时间，默认15s
     */
    private long timeout;
    /**
     * 代理
     */
    private String proxy;
    /**
     * 是否异步调用
     */
    private boolean async;
    /**
     * 是否单向调用
     */
    private boolean oneWay;
    /**
     * 注册服务
     */
    private RegistryService registryService;
    /**
     * 心跳间隔时间，默认30秒
     */
    private int heartbeatInterval;
    /**
     * 扫描并移除空闲连接时间，默认60秒
     */
    private int scanNotActiveChannelInterval;
    /**
     * 重试间隔时间
     */
    private int retryInterval = 1000;
    /**
     * 重试次数
     */
    private int retryTimes = 3;
    /**
     * 是否开启结果缓存
     */
    private boolean enableResultCache;
    /**
     * 缓存结果的时长，单位是毫秒
     */
    private int resultCacheExpire;
    /**
     * 是否开启直连服务
     */
    private boolean enableDirectServer;
    /**
     * 直连服务的地址
     */
    private String directServerUrl;

    public RpcClient(String registryAddress, String registryType, String serviceVersion, String serviceGroup,
                     String serializationType, String registryLoadBalanceType, long timeout, String proxy,
                     boolean async, boolean oneWay, int heartbeatInterval, int scanNotActiveChannelInterval,
                     int retryInterval, int retryTimes, boolean enableResultCache, int resultCacheExpire,
                     boolean enableDirectServer, String directServerUrl) {
        this.serviceVersion = serviceVersion;
        this.serviceGroup = serviceGroup;
        this.serializationType = serializationType;
        this.timeout = timeout;
        this.proxy = proxy;
        this.async = async;
        this.oneWay = oneWay;
        this.heartbeatInterval = heartbeatInterval;
        this.scanNotActiveChannelInterval = scanNotActiveChannelInterval;
        this.retryInterval = retryInterval;
        this.retryTimes = retryTimes;
        this.enableResultCache = enableResultCache;
        this.resultCacheExpire = resultCacheExpire;
        this.enableDirectServer = enableDirectServer;
        this.directServerUrl = directServerUrl;
        this.registryService = this.getRegistryService(registryAddress, registryType, registryLoadBalanceType);
    }

    private RegistryService getRegistryService(String registryAddress, String registryType,
                                               String registryLoadBalanceType) {
        if (StringUtils.isEmpty(registryType)) {
            throw new IllegalArgumentException("registry type is null");
        }
        RegistryService registryService = ExtensionLoader.getExtension(RegistryService.class, registryType);
        try {
            registryService.init(new RegistryConfig(registryAddress, registryType, registryLoadBalanceType));
        } catch (Exception e) {
            log.error("RpcClient init registry service throws exception:{}", e.getMessage());
            throw new RegistryException(e.getMessage(), e);
        }
        return registryService;
    }

    public <T> T create(Class<T> interfaceClass) {
        ProxyFactory proxyFactory = ExtensionLoader.getExtension(ProxyFactory.class, proxy);
        proxyFactory.init(new ProxyConfig(interfaceClass, serviceVersion, serviceGroup, timeout,
                RpcConsumer.getInstance().
                        setHeartbeatInterval(heartbeatInterval).
                        setScanNotActiveChannelInterval(scanNotActiveChannelInterval).
                        setRetryInterval(retryInterval).
                        setRetryTimes(retryTimes).
                        setEnableDirectServer(enableDirectServer).
                        setDirectServerUrl(directServerUrl),
                serializationType, async, oneWay, registryService, enableResultCache, resultCacheExpire));
        return proxyFactory.getProxy(interfaceClass);
    }

    public <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
        return new ObjectProxy<T>(interfaceClass, serviceVersion, serviceGroup, timeout,
                RpcConsumer.getInstance().
                        setHeartbeatInterval(heartbeatInterval).
                        setScanNotActiveChannelInterval(scanNotActiveChannelInterval).
                        setRetryInterval(retryInterval).
                        setRetryTimes(retryTimes).
                        setEnableDirectServer(enableDirectServer).
                        setDirectServerUrl(directServerUrl),
                serializationType, async, oneWay, registryService, enableResultCache, resultCacheExpire);
    }

    public void shutdown() {
        RpcConsumer.getInstance().close();
    }
}
