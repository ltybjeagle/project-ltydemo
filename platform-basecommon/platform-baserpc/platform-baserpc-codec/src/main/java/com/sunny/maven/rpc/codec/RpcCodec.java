package com.sunny.maven.rpc.codec;

import com.sunny.maven.rpc.serialization.api.Serialization;
import com.sunny.maven.rpc.serialization.jdk.JdkSerialization;

/**
 * @author SUNNY
 * @description: 实现编解码的接口，提供序列化和反序列化的默认方法
 * @create: 2022-12-28 17:19
 */
public interface RpcCodec {
    /**
     * 获取默认JDK序列化
     * @return Serialization
     */
    default Serialization getJdkSerialization() {
        return new JdkSerialization();
    }
}
