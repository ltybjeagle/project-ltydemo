package com.sunny.maven.rpc.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.sunny.maven.rpc.common.exception.SerializerException;
import com.sunny.maven.rpc.serialization.api.Serialization;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: Kryo Serialization
 * @create: 2023-01-12 22:51
 */
@Slf4j
@SPIClass
public class KryoSerialization implements Serialization {
    @Override
    public <T> byte[] serialize(T obj) {
        log.info("execute kryo serialize...");
        if (obj == null) {
            throw new SerializerException("serialize object is null");
        }
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(obj.getClass(), new JavaSerializer());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Output output = new Output(os);
        kryo.writeClassAndObject(output, obj);
        output.flush();
        output.close();
        byte[] bytes = os.toByteArray();
        try {
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new SerializerException(e.getMessage(), e);
        }
        return bytes;
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> cls) {
        log.info("execute kryo deserialize...");
        if (data == null) {
            throw new SerializerException("deserialize data is null");
        }
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(cls, new JavaSerializer());
        ByteArrayInputStream is = new ByteArrayInputStream(data);
        Input input = new Input(is);
        return (T) kryo.readClassAndObject(input);
    }
}
