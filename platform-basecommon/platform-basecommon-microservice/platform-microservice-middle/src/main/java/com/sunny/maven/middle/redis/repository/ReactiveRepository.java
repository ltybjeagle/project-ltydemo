package com.sunny.maven.middle.redis.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: ReactiveRedis接口定义
 * @create: 2022-10-17 16:39
 */
public interface ReactiveRepository {

    /**
     * 保存数据
     * @param key
     * @param value
     * @return
     */
    Mono<Boolean> saveObject(String key, Object value);
    /**
     * 保存数据(失效时间)
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    Mono<Boolean> saveObject(String key, Object value, Duration timeout);
    /**
     * 更新数据
     * @param key
     * @param value
     * @return
     */
    Mono<Boolean> updateObject(String key, Object value);
    /**
     * 删除数据
     * @param key
     * @return
     */
    Mono<Boolean> deleteObject(String key);
    /**
     * 查询数据
     * @param key
     * @return
     */
    Mono<Object> findObjectById(String key);
    /**
     * 查询所有数据
     * @return
     */
    Flux<Object> findAllObjects();
}
