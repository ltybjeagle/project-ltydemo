package com.sunny.maven.core.dal.encryption.api;

/**
 * @author SUNNY
 * @description: 解密接口定义
 * @create: 2023-05-24 22:05
 */
public interface IDecrypt {

    /**
     * 解密
     * @param result resultType的实例
     * @return T
     * @param <T> 类型
     * @throws IllegalAccessException 字段不可访问异常
     */
    <T> T decrypt(T result) throws IllegalAccessException;
}
