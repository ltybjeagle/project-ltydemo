package com.sunny.maven.rpc.serialization.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.sunny.maven.rpc.common.exception.SerializerException;
import com.sunny.maven.rpc.serialization.api.Serialization;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUNNY
 * @description: rotostuff序列化
 * @create: 2023-01-13 10:43
 */
@Slf4j
@SPIClass
public class ProtostuffSerialization implements Serialization {

    private Map<Class<?>, Schema<?>> cacheSchema = new ConcurrentHashMap<>();
    private Objenesis objenesis = new ObjenesisStd(true);

    private <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cacheSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cacheSchema.put(cls, schema);
            }
        }
        return schema;
    }

    /**
     * 序列化（对象 -> 字节数组）
     * @param obj 原始对象
     * @param <T>
     * @return
     */
    @Override
    public <T> byte[] serialize(T obj) {
        log.info("execute protostuff serialize...");
        if (obj == null) {
            throw new SerializerException("serialize object is null");
        }
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = this.getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 反序列化（字节数组 -> 对象）
     * @param data 字节数组
     * @param cls 对象类对象
     * @param <T>
     * @return
     */
    @Override
    public <T> T deSerialize(byte[] data, Class<T> cls) {
        log.info("execute protostuff deserialize...");
        if (data == null) {
            throw new SerializerException("deserialize data is null");
        }
        try {
            T message = objenesis.newInstance(cls);
            Schema<T> schema = this.getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }
}
