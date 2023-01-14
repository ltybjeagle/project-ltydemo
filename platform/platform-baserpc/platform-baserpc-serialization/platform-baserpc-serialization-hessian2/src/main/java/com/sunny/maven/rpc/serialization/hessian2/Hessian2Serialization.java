package com.sunny.maven.rpc.serialization.hessian2;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.sunny.maven.rpc.common.exception.SerializerException;
import com.sunny.maven.rpc.serialization.api.Serialization;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: Hessian2序列化与反序列化
 * @create: 2023-01-11 00:25
 */
@Slf4j
@SPIClass
public class Hessian2Serialization implements Serialization {
    @Override
    public <T> byte[] serialize(T obj) {
        log.info("execute hessian2 serialize...");
        if (obj == null) {
            throw new SerializerException("serialize object is null");
        }
        byte[] result = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(byteArrayOutputStream);
        try {
            hessian2Output.startMessage();
            hessian2Output.writeObject(obj);
            hessian2Output.flush();
            hessian2Output.completeMessage();
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                hessian2Output.close();
                byteArrayOutputStream.close();
            } catch (IOException ex) {
                throw new SerializerException(ex.getMessage(), ex);
            }
        }
        return result;
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> cls) {
        log.info("execute hessian2 deserialize...");
        if (data == null) {
            throw new SerializerException("deserialize data is null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        Hessian2Input hessian2Input = new Hessian2Input(byteArrayInputStream);
        T object = null;
        try {
            hessian2Input.startMessage();
            object = (T) hessian2Input.readObject();
            hessian2Input.completeMessage();
        } catch (IOException e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                hessian2Input.close();
                byteArrayInputStream.close();
            } catch (IOException ex) {
                throw new SerializerException(ex.getMessage(), ex);
            }
        }
        return object;
    }
}
