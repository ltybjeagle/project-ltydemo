package com.sunny.maven.rpc.proxy.api.object;

import com.sunny.maven.rpc.cache.result.CacheResultKey;
import com.sunny.maven.rpc.cache.result.CacheResultManager;
import com.sunny.maven.rpc.common.utils.StringUtils;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.enumeration.RpcType;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import com.sunny.maven.rpc.proxy.api.async.IAsyncObjectProxy;
import com.sunny.maven.rpc.proxy.api.consumer.Consumer;
import com.sunny.maven.rpc.proxy.api.future.RpcFuture;
import com.sunny.maven.rpc.ratelimiter.api.RateLimiterInvoker;
import com.sunny.maven.rpc.reflect.api.ReflectInvoker;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 对象代理类
 * @create: 2023-01-01 21:11
 */
@Slf4j
public class ObjectProxy<T> implements IAsyncObjectProxy, InvocationHandler {

    /**
     * 接口的Class对象
     */
    private Class<T> clazz;
    /**
     * 服务版本号
     */
    private String serviceVersion;
    /**
     * 服务分组
     */
    private String serviceGroup;
    /**
     * 超时时间，默认15s
     */
    private long timeout = 15000;
    /**
     * 服务消费者
     */
    private Consumer consumer;
    /**
     * 序列化类型
     */
    private String serializationType;
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
     * 是否开启结果缓存
     */
    private boolean enableResultCache;
    /**
     * 结果缓存管理器
     */
    private CacheResultManager<Object> cacheResultManager;
    /**
     * 反射调用方法
     */
    private ReflectInvoker reflectInvoker;
    /**
     * 容错Class类
     */
    private Class<?> fallbackClass;

    /**
     * 是否开启限流
     */
    private boolean enableRateLimiter;
    /**
     * 限流SPI接口
     */
    private RateLimiterInvoker rateLimiterInvoker;
    /**
     * 当限流失败时的处理策略
     */
    private String rateLimiterFailStrategy;

    public ObjectProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ObjectProxy(Class<T> clazz, String serviceVersion, String serviceGroup, long timeout, Consumer consumer,
                       String serializationType, boolean async, boolean oneWay, RegistryService registryService,
                       boolean enableResultCache, int resultCacheExpire, String reflectType, String fallbackClassName,
                       boolean enableRateLimiter, String rateLimiterType, int permits, int milliSeconds,
                       String rateLimiterFailStrategy, Class<?> fallbackClass) {
        this.clazz = clazz;
        this.serviceVersion = serviceVersion;
        this.serviceGroup = serviceGroup;
        this.timeout = timeout;
        this.consumer = consumer;
        this.serializationType = serializationType;
        this.async = async;
        this.oneWay = oneWay;
        this.registryService = registryService;
        this.enableResultCache = enableResultCache;
        if (resultCacheExpire <= 0) {
            resultCacheExpire = RpcConstants.RPC_SCAN_RESULT_CACHE_EXPIRE;
        }
        this.cacheResultManager = CacheResultManager.getInstance(resultCacheExpire, enableResultCache);
        this.reflectInvoker = ExtensionLoader.getExtension(ReflectInvoker.class, reflectType);
        this.fallbackClass = this.getFallbackClass(fallbackClassName, fallbackClass);
        this.enableRateLimiter = enableRateLimiter;
        this.initRateLimiter(rateLimiterType, permits, milliSeconds);
        if (StringUtils.isEmpty(rateLimiterFailStrategy)) {
            rateLimiterFailStrategy = RpcConstants.RATE_LIMITER_FAIL_STRATEGY_DIRECT;
        }
        this.rateLimiterFailStrategy = rateLimiterFailStrategy;
    }

    /**
     * 初始化限流器
     */
    private void initRateLimiter(String rateLimiterType, int permits, int milliSeconds) {
        if (enableRateLimiter) {
            rateLimiterType = StringUtils.isEmpty(rateLimiterType) ?
                    RpcConstants.DEFAULT_RATELIMITER_INVOKER : rateLimiterType;
            this.rateLimiterInvoker = ExtensionLoader.getExtension(RateLimiterInvoker.class, rateLimiterType);
            this.rateLimiterInvoker.init(permits, milliSeconds);
        }
    }

    /**
     * 优先使用fallbackClass，如果fallbackClass为空，则使用fallbackClassName
     */
    private Class<?> getFallbackClass(String fallbackClassName, Class<?> fallbackClass) {
        if (this.isFallbackClassEmpty(fallbackClass)) {
            try {
                if (!StringUtils.isEmpty(fallbackClassName)) {
                    fallbackClass = Class.forName(fallbackClassName);
                }
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage());
            }
        }
        return fallbackClass;
    }

    /**
     * 容错class为空
     */
    private boolean isFallbackClassEmpty(Class<?> fallbackClass) {
        return fallbackClass == null || fallbackClass == RpcConstants.DEFAULT_FALLBACK_CLASS ||
                RpcConstants.DEFAULT_FALLBACK_CLASS.equals(fallbackClass);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        // 开启缓存，直接调用方法请求服务提供者
        if (enableResultCache) {
            return invokeSendRequestMethodCache(method, args);
        }
        return invokeSendRequestMethodWithFallback(method, args);
    }

    private Object invokeSendRequestMethodCache(Method method, Object[] args) throws Throwable {
        // 开启缓存，则处理缓存
        CacheResultKey cacheResultKey = new CacheResultKey(method.getDeclaringClass().getName(), method.getName(),
                method.getParameterTypes(), args, serviceVersion, serviceGroup);
        Object obj = this.cacheResultManager.get(cacheResultKey);
        if (obj == null) {
            obj = invokeSendRequestMethodWithFallback(method, args);
            if (obj != null) {
                cacheResultKey.setCacheTimeStamp(System.currentTimeMillis());
                this.cacheResultManager.put(cacheResultKey, obj);
            }
        }
        return obj;
    }

    /**
     * 以容错方式真正发送请求
     */
    private Object invokeSendRequestMethodWithFallback(Method method, Object[] args) throws Exception {
        try {
            return this.invokeSendRequestMethodWithRateLimiter(method, args);
        } catch (Throwable t) {
            // fallbackClass不为空，则执行容错处理
            if (isFallbackClassEmpty(fallbackClass)) {
                return null;
            }
            return this.getFallbackResult(method, args);
        }
    }

    /**
     * 以限流方式发送请求
     */
    private Object invokeSendRequestMethodWithRateLimiter(Method method, Object[] args) throws Exception {
        Object result = null;
        if (enableRateLimiter) {
            if (rateLimiterInvoker.tryAcquire()) {
                try {
                    result = invokeSendRequestMethod(method, args);
                } finally {
                    rateLimiterInvoker.release();
                }
            } else {
                result = this.invokeFailRateLimiterMethod(method, args);
            }
        } else {
            result = invokeSendRequestMethod(method, args);
        }
        return result;
    }

    /**
     * 执行限流失败时的处理逻辑
     */
    private Object invokeFailRateLimiterMethod(Method method, Object[] args) throws Exception {
        log.info("execute {} fail rate limiter strategy...", rateLimiterFailStrategy);
        switch (rateLimiterFailStrategy) {
            case RpcConstants.RATE_LIMITER_FAIL_STRATEGY_EXCEPTION:
            case RpcConstants.RATE_LIMITER_FAIL_STRATEGY_FALLBACK:
                return this.getFallbackResult(method, args);
            case RpcConstants.RATE_LIMITER_FAIL_STRATEGY_DIRECT:
                return this.invokeSendRequestMethod(method, args);
        }
        return this.invokeSendRequestMethod(method, args);
    }

    /**
     * 真正发送请求调用远程方法
     */
    private Object invokeSendRequestMethod(Method method, Object[] args) throws Exception {
        RpcProtocol<RpcRequest> requestRpcProtocol = getSendRequest(method, args);
        RpcFuture rpcFuture = this.consumer.sendRequest(requestRpcProtocol, registryService);
        return rpcFuture == null ? null : timeout > 0 ? rpcFuture.get(timeout, TimeUnit.MILLISECONDS) :
                rpcFuture.get();
    }

    /**
     * 获取容错结果
     */
    private Object getFallbackResult(Method method, Object[] args) {
        try {
            return reflectInvoker.invokeMethod(fallbackClass.newInstance(), fallbackClass, method.getName(),
                    method.getParameterTypes(), args);
        } catch (Throwable ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * 封装请求协议对象
     */
    private RpcProtocol<RpcRequest> getSendRequest(Method method, Object[] args) {
        RpcProtocol<RpcRequest> requestRpcProtocol = new RpcProtocol<>();

        requestRpcProtocol.setHeader(RpcHeaderFactory.getRequestHeader(serializationType, RpcType.REQUEST.getType()));

        RpcRequest request = new RpcRequest();
        request.setVersion(this.serviceVersion);
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setGroup(this.serviceGroup);
        request.setParameters(args);
        request.setOneway(oneWay);
        request.setAsync(async);
        requestRpcProtocol.setBody(request);

        // Debug
        log.debug(method.getDeclaringClass().getName());
        log.debug(method.getName());

        if (method.getParameterTypes() != null && method.getParameterTypes().length > 0) {
            for (int i = 0; i < method.getParameterTypes().length; ++i) {
                log.debug(method.getParameterTypes()[i].getName());
            }
        }

        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; ++i) {
                log.debug(args[i].toString());
            }
        }
        return requestRpcProtocol;
    }

    @Override
    public RpcFuture call(String funcName, Object... args) {
        RpcProtocol<RpcRequest> request = createRequest(this.clazz.getName(), funcName, args);
        RpcFuture rpcFuture = null;
        try {
            rpcFuture = this.consumer.sendRequest(request, registryService);
        } catch (Exception e) {
            log.error("async all throws exception:{}", e.getMessage());
        }
        return rpcFuture;
    }

    private RpcProtocol<RpcRequest> createRequest(String className, String methodName, Object[] args) {
        RpcProtocol<RpcRequest> requestRpcProtocol = new RpcProtocol<>();

        requestRpcProtocol.setHeader(RpcHeaderFactory.getRequestHeader(serializationType, RpcType.REQUEST.getType()));

        RpcRequest request = new RpcRequest();
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameters(args);
        request.setVersion(this.serviceVersion);
        request.setGroup(this.serviceGroup);

        Class[] parameterTypes = new Class[args.length];
        // Get the right class type
        for (int i = 0; i < args.length; ++i) {
            parameterTypes[i] = getClassType(args[i]);
        }
        request.setParameterTypes(parameterTypes);
        requestRpcProtocol.setBody(request);

        log.debug(className);
        log.debug(methodName);
        for (Class parameterType : parameterTypes) {
            log.debug(parameterType.getName());
        }
        for (Object arg : args) {
            log.debug(arg.toString());
        }

        return requestRpcProtocol;
    }

    private Class<?> getClassType(Object obj) {
        Class<?> classType = obj.getClass();
        String typeName = classType.getName();
        switch (typeName) {
            case "java.lang.Integer":
                return Integer.TYPE;
            case "java.lang.Long":
                return Long.TYPE;
            case "java.lang.Float":
                return Float.TYPE;
            case "java.lang.Double":
                return Double.TYPE;
            case "java.lang.Character":
                return Character.TYPE;
            case "java.lang.Boolean":
                return Boolean.TYPE;
            case "java.lang.Short":
                return Short.TYPE;
            case "java.lang.Byte":
                return Byte.TYPE;
        }
        return classType;
    }
}
