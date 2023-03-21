package com.sunny.maven.rpc.constants;

/**
 * @author SUNNY
 * @description: 常量类
 * @create: 2022-12-27 16:01
 */
public class RpcConstants {
    /**
     * 消息头，固定32个字节
     */
    public static final int HEADER_TOTAL_LEN = 32;
    /**
     * 魔数
     */
    public static final short MAGIC = 0x10;
    /**
     * 版本号
     */
    public static final byte VERSION = 0x1;
    /**
     * REFLECT_TYPE_JDK
     */
    public static final String REFLECT_TYPE_JDK = "jdk";
    /**
     * REFLECT_TYPE_CGLIB
     */
    public static final String REFLECT_TYPE_CGLIB = "cglib";
    /**
     * jdk动态代理
     */
    public static final String PROXY_JDK = "jdk";
    /**
     * javassist动态代理
     */
    public static final String PROXY_JAVASSIST = "javassist";
    /**
     * cglib动态代理
     */
    public static final String PROXY_CGLIB = "cglib";
    /**
     * 初始化的方法
     */
    public static final String INIT_METHOD_NAME = "init";
    /**
     * zookeeper
     */
    public static final String REGISTRY_CENTER_ZOOKEEPER = "zookeeper";
    /**
     * nacos
     */
    public static final String REGISTRY_CENTER_NACOS = "nacos";
    /**
     * apoll
     */
    public static final String REGISTRY_CENTER_APOLL = "apoll";
    /**
     * etcd
     */
    public static final String REGISTRY_CENTER_ETCD = "etcd";
    /**
     * eureka
     */
    public static final String REGISTRY_CENTER_EUREKA = "eureka";
    /**
     * protostuff 序列化
     */
    public static final String SERIALIZATION_PROTOSTUFF = "protostuff";
    /**
     * fst 序列化
     */
    public static final String SERIALIZATION_FST = "fst";
    /**
     * hessian2 序列化
     */
    public static final String SERIALIZATION_HESSIAN2 = "hessian2";
    /**
     * jdk 序列化
     */
    public static final String SERIALIZATION_JDK = "jdk";
    /**
     * json 序列化
     */
    public static final String SERIALIZATION_JSON = "json";
    /**
     * kryo 序列化
     */
    public static final String SERIALIZATION_KRYO = "kryo";
    /**
     * 基于ZK的一致性Hash负载均衡
     */
    public static final String SERVICE_LOAD_BALANCER_ZKCONSISTENTHASH = "zkconsistenthash";
    /**
     * 基于随机算法的负载均衡
     */
    public static final String SERVICE_LOAD_BALANCER_RANDOM = "random";
    /**
     * 增强型负载均衡前缀
     */
    public static final String SERVICE_ENHANCED_LOAD_BALANCER_PREFIX = "enhanced_";
    /**
     * 最小权重
     */
    public static final int SERVICE_WEIGHT_MIN = 1;
    /**
     * 最大权重
     */
    public static final int SERVICE_WEIGHT_MAX = 100;
    /**
     * 心跳ping消息
     */
    public static final String HEARTBEAT_PING = "ping";
    /**
     * 心跳pong消息
     */
    public static final String HEARTBEAT_PONG = "pong";
    /**
     * decoder
     */
    public static final String CODEC_DECODER = "decoder";
    /**
     * encoder
     */
    public static final String CODEC_ENCODER = "encoder";
    /**
     * handler
     */
    public static final String CODEC_HANDLER = "handler";
    /**
     * server-idle-handler
     */
    public static final String CODEC_SERVER_IDLE_HANDLER = "server-idle-handler";
    /**
     * client-idle-handler
     */
    public static final String CODEC_CLIENT_IDLE_HANDLER = "client-idle-handler";
    /**
     * 默认的重试时间间隔，1s
     */
    public static final int DEFAULT_RETRY_INTERVAL = 1000;
    /**
     * 默认的重试次数，无限重试
     */
    public static final int DEFAULT_RETRY_TIMES = Integer.MAX_VALUE;
    /**
     * RPC框架默认版本号
     */
    public static final String RPC_COMMON_DEFAULT_VERSION = "1.0.0";
    /**
     * RPC框架默认的分组
     */
    public static final String RPC_COMMON_DEFAULT_GROUP = "";
    /**
     * RPC框架默认的心跳间隔时间
     */
    public static final int RPC_COMMON_DEFAULT_HEARTBEATINTERVAL = 30000;
    /**
     * 服务提供者默认的扫描并移除不活跃连接的间隔时间
     */
    public static final int RPC_COMMON_DEFAULT_SCANNOTACTIVECHANNELINTERVAL = 60000;
    /**
     * 服务消费者默认的注册中心类型
     */
    public static final String RPC_REFERENCE_DEFAULT_REGISTRYTYPE = "zookeeper";
    /**
     * 服务消费者默认的注册中心地址
     */
    public static final String RPC_REFERENCE_DEFAULT_REGISTRYADDRESS = "127.0.0.1:2181";
    /**
     * 服务消费者默认负载均衡类型
     */
    public static final String RPC_REFERENCE_DEFAULT_LOADBALANCETYPE = "zkconsistenthash";
    /**
     * 服务消费者默认的序列化方式
     */
    public static final String RPC_REFERENCE_DEFAULT_SERIALIZATIONTYPE = "protostuff";
    /**
     * 服务消费者默认的超时时间
     */
    public static final int RPC_REFERENCE_DEFAULT_TIMEOUT = 5000;
    /**
     * 服务消费者默认的代理
     */
    public static final String RPC_REFERENCE_DEFAULT_PROXY = "jdk";
    /**
     * 服务消费者默认的重试间隔时间
     */
    public static final int RPC_REFERENCE_DEFAULT_RETRYINTERVAL = 1000;
    /**
     * 服务消费者默认的重试次数
     */
    public static final int RPC_REFERENCE_DEFAULT_RETRYTIMES = 3;
    /**
     * 默认的结果缓存时长，默认5秒，单位是毫秒
     */
    public static final int RPC_SCAN_RESULT_CACHE_EXPIRE = 5000;
    /**
     * 默认直连服务的地址
     */
    public static final String RPC_COMMON_DEFAULT_DIRECT_SERVER = "";
    /**
     * IP和端口的分隔符
     */
    public static final String IP_PORT_SPLIT = ":";
    /**
     * 服务消费者直连多个服务提供者时，多个地址之间的分隔符
     */
    public static final String RPC_MULTI_DIRECT_SERVERS_SEPARATOR = ",";
    /**
     * 默认的核心线程数
     */
    public static final int DEFAULT_CORE_POOL_SIZE = 16;
    /**
     * 默认的最大线程数
     */
    public static final int DEFAULT_MAXI_MUM_POOL_SIZE = 16;
    /**
     * 默认空闲保持时间
     */
    public static final long DEFAULT_KEEP_ALIVE_TIME = 600;
    /**
     * 线程池队列最大容量
     */
    public static final int DEFAULT_QUEUE_CAPACITY = 65535;
    /**
     * 打印流量
     */
    public static final String FLOW_POST_PROCESSOR_PRINT = "print";
    /**
     * 打印异常
     */
    public static final String EXCEPTION_POST_PROCESSOR_PRINT = "print";
    /**
     * 默认策略
     */
    public static final String RPC_CONNECTION_DISUSE_STRATEGY_DEFAULT = "strategy_default";
    /**
     * 默认的缓存区大小
     */
    public static final int DEFAULT_BUFFER_SIZE = 655350;
    /**
     * 默认容错class
     */
    public static final Class<?> DEFAULT_FALLBACK_CLASS = void.class;
    /**
     * 默认容错class名称
     */
    public static final String DEFAULT_FALLBACK_CLASS_NAME = "";
    /**
     * 默认的反射类型
     */
    public static final String DEFAULT_REFLECT_TYPE = "jdk";
    /**
     * 默认返回值
     */
    public static final String DEFAULT_RETURN_VALUE = "";
    /**
     * 默认限流策略
     */
    public static final String DEFAULT_RATELIMITER_INVOKER = "counter";
    /**
     * 默认限流阈值
     */
    public static final int DEFAULT_RATELIMITER_PERMITS = 100;
    /**
     * 默认限流时间值，毫秒
     */
    public static final int DEFAULT_RATELIMITER_MILLI_SECONDS = 1000;
    /**
     * 默认熔断时间值，毫秒
     */
    public static final int DEFAULT_FUSING_MILLI_SECONDS = 5000;
    /**
     * 默认熔断总次数或错误率
     */
    public static final double DEFAULT_FUSING_TOTAL_FAILURE = 10;
    /**
     * 异常处理
     */
    public static final String RATE_LIMITER_FAIL_STRATEGY_EXCEPTION = "exception";
    /**
     * 直接调用原来方法
     */
    public static final String RATE_LIMITER_FAIL_STRATEGY_DIRECT = "direct";
    /**
     * 容错降级处理
     */
    public static final String RATE_LIMITER_FAIL_STRATEGY_FALLBACK = "fallback";
    /**
     * 默认熔断规则
     */
    public static final String DEFAULT_FUSING_INVOKER = "counter";
    /**
     * 熔断状态：关闭
     */
    public static final int FUSING_STATUS_CLOSED = 1;
    /**
     * 熔断状态：半开启
     */
    public static final int FUSING_STATUS_HALF_OPEN = 2;
    /**
     * 熔断状态：开启
     */
    public static final int FUSING_STATUS_OPEN = 3;
    /**
     * 初始化状态
     */
    public static final int FUSING_WAIT_STATUS_INIT = 0;
    /**
     * 等待
     */
    public static final int FUSING_WAIT_STATUS_WAITING = 1;
    /**
     * 成功
     */
    public static final int FUSING_WAIT_STATUS_SUCCESS = 2;
    /**
     * 失败
     */
    public static final int FUSING_WAIT_STATUS_FAILED = 3;
}
