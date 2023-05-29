package com.sunny.maven.core.dal.encryption.api.impl;

import com.sunny.maven.core.annotation.database.encryption.EncryptTransaction;
import com.sunny.maven.core.dal.encryption.api.IDecrypt;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author SUNNY
 * @description: 解密实现类
 * @create: 2023/5/24 22:42
 */
public class DecryptImpl implements IDecrypt {
    private volatile static DecryptImpl instance;
    /**
     * 解密
     * @param result resultType的实例
     * @return T
     * @param <T> 类型
     * @throws IllegalAccessException 字段不可访问异常
     */
    @Override
    public <T> T decrypt(T result) throws IllegalAccessException {
        // 取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            // 取出所有被DecryptTransaction注解的字段
            EncryptTransaction encryptTransaction = field.getAnnotation(EncryptTransaction.class);
            if (!Objects.isNull(encryptTransaction)) {
                field.setAccessible(true);
                Object object = field.get(result);
                // String值解密
                if (object instanceof String value) {
                    try {
                        // 对注解的字段进行逐一解密
                        // TODO 扩展实现字段加密并赋值
                        field.set(result, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public static DecryptImpl getInstance() {
        if (instance == null) {
            synchronized (DecryptImpl.class) {
                if (instance == null) {
                    instance = new DecryptImpl();
                }
            }
        }
        return instance;
    }

    private DecryptImpl() {}
}
