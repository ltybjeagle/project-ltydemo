package com.sunny.maven.rpc.serialization.fst;

import com.sunny.maven.rpc.common.exception.SerializerException;
import com.sunny.maven.rpc.serialization.api.Serialization;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;

/**
 * @author SUNNY
 * @description: Fst Serialization
 * @create: 2023-01-11 17:10
 */
@Slf4j
@SPIClass
public class FstSerialization implements Serialization {
    @Override
    public <T> byte[] serialize(T obj) {
        log.info("execute fst serialize...");
        if (obj == null) {
            throw new SerializerException("serialize object is null");
        }
        FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();
        return conf.asByteArray(obj);
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> cls) {
        log.info("execute fst deserialize...");
        if (data == null) {
            throw new SerializerException("deserialize data is null");
        }
        FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();
        return (T) conf.asObject(data);
    }
}
