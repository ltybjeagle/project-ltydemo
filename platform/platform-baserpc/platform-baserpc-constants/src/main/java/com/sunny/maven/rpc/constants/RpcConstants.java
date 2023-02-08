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
}
