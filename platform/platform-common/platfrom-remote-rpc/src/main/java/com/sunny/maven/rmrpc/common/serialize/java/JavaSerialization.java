package com.sunny.maven.rmrpc.common.serialize.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/17 21:17
 * @description：java序列化
 * @modified By：
 * @version: 1.0.0
 */
public class JavaSerialization {
    private static final Logger logger = LoggerFactory.getLogger(JavaSerialization.class);
    private static byte[] empty_byte = new byte[0];

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public Object byteToObject(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("byte[] to Object fail, exception : {}", e.getMessage());
        }
        return null;
    }

    /**
     * 对象序列化
     * @param obj
     * @return
     */
    public byte[] objectToByte(Object obj) {
        if (obj == null) {
            return empty_byte;
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Object to byte[] fail, exception : {}", e.getMessage());
        }
        return empty_byte;
    }

    public static final JavaSerialization getInstance() {
        return JavaSerializationHolder.javaSerialization;
    }

    private static class JavaSerializationHolder {
        private static final JavaSerialization javaSerialization = new JavaSerialization();
    }

    private JavaSerialization() {
    }
}
