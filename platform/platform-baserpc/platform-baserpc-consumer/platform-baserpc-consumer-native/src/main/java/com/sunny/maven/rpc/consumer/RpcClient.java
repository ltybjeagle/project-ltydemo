package com.sunny.maven.rpc.consumer;

import com.sunny.maven.rpc.common.exception.RegistryException;
import com.sunny.maven.rpc.consumer.common.RpcConsumer;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;
import com.sunny.maven.rpc.proxy.api.async.IAsyncObjectProxy;
import com.sunny.maven.rpc.proxy.api.config.ProxyConfig;
import com.sunny.maven.rpc.proxy.api.object.ObjectProxy;
import com.sunny.maven.rpc.proxy.jdk.JdkProxyFactory;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.registry.zookeeper.ZookeeperRegistryService;
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

    public RpcClient(String registryAddress, String registryType, String serviceVersion, String serviceGroup,
                     String serializationType, String registryLoadBalanceType, long timeout, String proxy,
                     boolean async, boolean oneWay) {
        this.serviceVersion = serviceVersion;
        this.serviceGroup = serviceGroup;
        this.serializationType = serializationType;
        this.timeout = timeout;
        this.proxy = proxy;
        this.async = async;
        this.oneWay = oneWay;
        this.registryService = this.getRegistryService(registryAddress, registryType, registryLoadBalanceType);
    }

    private RegistryService getRegistryService(String registryAddress, String registryType,
                                               String registryLoadBalanceType) {
        if (StringUtils.isEmpty(registryType)) {
            throw new IllegalArgumentException("registry type is null");
        }
        //TODO 后续SPI扩展
        RegistryService registryService = new ZookeeperRegistryService();
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
                RpcConsumer.getInstance(), serializationType, async, oneWay, registryService));
        return proxyFactory.getProxy(interfaceClass);
    }

    public <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
        return new ObjectProxy<T>(interfaceClass, serviceVersion, serviceGroup, timeout, RpcConsumer.getInstance(),
                serializationType, async, oneWay, registryService);
    }

    public void shutdown() {
        RpcConsumer.getInstance().close();
    }
}
