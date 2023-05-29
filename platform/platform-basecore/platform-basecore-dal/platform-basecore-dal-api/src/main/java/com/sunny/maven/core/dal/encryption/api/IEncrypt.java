package com.sunny.maven.core.dal.encryption.api;

import java.lang.reflect.Field;

/**
 * @author SUNNY
 * @description: 加密接口定义
 * @create: 2023/5/24 22:11
 */
public interface IEncrypt {

    /**
     * 加密
     * @param declareFields 加密字段
     * @param paramsObject 对象
     * @return T
     * @param <T> 入参类型
     * @throws IllegalAccessException 不可访问
     */
    <T> T encrypt(Field[] declareFields, T paramsObject) throws IllegalAccessException;

    /**
     * 加密
     * @param value 加密值
     * @return String
     */
    String encrypt(String value);
}
