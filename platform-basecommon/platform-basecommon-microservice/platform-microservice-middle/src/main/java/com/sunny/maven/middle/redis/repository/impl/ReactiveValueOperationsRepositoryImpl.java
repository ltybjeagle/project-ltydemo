package com.sunny.maven.middle.redis.repository.impl;

import com.sunny.maven.middle.redis.repository.ReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author SUNNY
 * @description: ReactiveRedisRepository实现类
 * @create: 2022-10-17 16:55
 */
@Repository
public class ReactiveValueOperationsRepositoryImpl implements ReactiveRepository {

    private ReactiveValueOperations<String, Object> reactiveValueOperations;
    private static final String PRE_CACHE_KEY = "DEFAULT:";

    /**
     * 保存数据
     * @param key
     * @param value
     * @return
     */
    @Override
    public Mono<Boolean> saveObject(String key, Object value) {
        return reactiveValueOperations.set(key, value);
    }
    /**
     * 保存数据(失效时间)
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    @Override
    public Mono<Boolean> saveObject(String key, Object value, Duration timeout) {
        return reactiveValueOperations.set(key, value, timeout);
    }
    /**
     * 更新数据
     * @param key
     * @param value
     * @return
     */
    @Override
    public Mono<Boolean> updateObject(String key, Object value) {
        return reactiveValueOperations.set(key, value);
    }
    /**
     * 删除数据
     * @param key
     * @return
     */
    @Override
    public Mono<Boolean> deleteObject(String key) {
        return reactiveValueOperations.delete(key);
    }
    /**
     * 查询数据
     * @param key
     * @return
     */
    @Override
    public Mono<Object> findObjectById(String key) {
        return reactiveValueOperations.get(key);
    }
    /**
     * 查询所有数据
     * @return
     */
    @Override
    public Flux<Object> findAllObjects() {
        return Flux.empty();
    }

    /**
     * 构造函数
     * @param reactiveValueOperations
     */
    @Autowired
    public ReactiveValueOperationsRepositoryImpl(ReactiveValueOperations<String, Object> reactiveValueOperations) {
        this.reactiveValueOperations = reactiveValueOperations;
    }
}
