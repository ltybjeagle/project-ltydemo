package com.sunny.maven.springframework.beans;

/**
 * @author SUNNY
 * @description: 定义 Bean 异常
 * @create: 2022-12-14 15:50
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
