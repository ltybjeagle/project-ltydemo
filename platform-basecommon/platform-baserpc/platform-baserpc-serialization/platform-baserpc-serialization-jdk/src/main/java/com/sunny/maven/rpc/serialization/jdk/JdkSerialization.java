package com.sunny.maven.rpc.serialization.jdk;

import com.sunny.maven.rpc.common.exception.SerializerException;
import com.sunny.maven.rpc.serialization.api.Serialization;

import java.io.*;

/**
 * @author SUNNY
 * @description: Jdk Serialization
 * @create: 2022-12-28 17:03
 */
public class JdkSerialization implements Serialization {
    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new SerializerException("serialize object is null");
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(obj);
            return os.toByteArray();
        } catch (IOException e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> cls) {
        if (data == null) {
            throw new SerializerException("deserialize data is null");
        }
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            ObjectInputStream in = new ObjectInputStream(is);
            return (T) in.readObject();
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }
}
