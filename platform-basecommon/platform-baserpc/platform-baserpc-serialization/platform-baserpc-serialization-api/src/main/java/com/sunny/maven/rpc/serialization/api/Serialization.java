package com.sunny.maven.rpc.serialization.api;

/**
 * @author SUNNY
 * @description: 序列化接口
 * @create: 2022-12-28 16:51
 */
public interface Serialization {
    /**
     * 序列化
     * @param obj 原始对象
     * @param <T> 对象类型
     * @return 字节数组
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     * @param data 字节数组
     * @param cls 对象类对象
     * @param <T> 对象类型
     * @return 对象
     */
    <T> T deSerialize(byte[] data, Class<T> cls);
}
