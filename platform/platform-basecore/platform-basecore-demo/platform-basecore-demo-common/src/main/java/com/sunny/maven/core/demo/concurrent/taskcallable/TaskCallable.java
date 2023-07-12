package com.sunny.maven.core.demo.concurrent.taskcallable;

/**
 * @author SUNNY
 * @description: 定义回调接口
 * @create: 2023/7/10 18:52
 */
public interface TaskCallable<T> {
    T callable(T t);
}
