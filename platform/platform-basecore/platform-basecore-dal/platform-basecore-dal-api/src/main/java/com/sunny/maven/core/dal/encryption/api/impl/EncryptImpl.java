package com.sunny.maven.core.dal.encryption.api.impl;

import com.sunny.maven.core.annotation.database.encryption.EncryptTransaction;
import com.sunny.maven.core.dal.encryption.api.IEncrypt;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author SUNNY
 * @description: 加密实现类
 * @create: 2023/5/25 6:24
 */
public class EncryptImpl implements IEncrypt {
    private volatile static EncryptImpl instance;
    /**
     * 加密
     * @param declareFields 加密字段
     * @param paramsObject 对象
     * @return T
     * @param <T> 入参类型
     * @throws IllegalAccessException 不可访问
     */
    @Override
    public <T> T encrypt(Field[] declareFields, T paramsObject) throws IllegalAccessException {
        // 取出所有被EncryptTransaction注解的字段
        for (Field field : declareFields) {
            EncryptTransaction encryptTransaction = field.getAnnotation(EncryptTransaction.class);
            if (!Objects.isNull(encryptTransaction)) {
                field.setAccessible(true);
                Object object = field.get(paramsObject);
                // 暂时只实现String类型的加密
                // TODO 扩展其他类型
                if (object instanceof String value) {
                    try {
                        // 对注解的字段进行加密
                        // TODO 扩展实现字段加密并赋值
                        field.set(paramsObject, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return paramsObject;
    }

    /**
     * 加密
     * @param value 加密值
     * @return String
     */
    @Override
    public String encrypt(String value) {
        // 对value进行加密
        // TODO 扩展实现加密
        return value;
    }

    /**
     * 获取单例实例对象(采用双重检查锁定的方式)
     * @return EncryptImpl
     */
    public static EncryptImpl getInstance() {
        if (instance == null) {
            synchronized (EncryptImpl.class) {
                if (instance == null) {
                    instance = new EncryptImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造函数
     */
    private EncryptImpl() {}
}
