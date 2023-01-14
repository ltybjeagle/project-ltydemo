package com.sunny.maven.middle.security;

import com.sunny.maven.middle.redis.repository.ReactiveRepository;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

/**
 * @author SUNNY
 * @description: 认证缓存
 * @create: 2022-11-04 17:55
 */
public class JwtTokenByRedis {
    /**
     * 缓存KEY前缀
     */
    private final String PRE_CACHE_KEY;
    /**
     * 认证缓存对象
     */
    private ReactiveRepository reactiveValueOperationsRepositoryImpl;

    /**
     * 保存数据(失效时间)
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean saveObject(String key, Object value, Duration timeout) {
        Mono<Boolean> isResult =
                reactiveValueOperationsRepositoryImpl.saveObject(PRE_CACHE_KEY + key, value, timeout);
        return isResult.block();
    }

    /**
     * 查询数据
     * @param key
     * @return
     */
    public String findObjectById(String key) {
        Mono<Object> jwtTokenUserObject =
                reactiveValueOperationsRepositoryImpl.findObjectById(PRE_CACHE_KEY + key);
        Optional opt = Optional.ofNullable(jwtTokenUserObject.block(Duration.ofSeconds(3)));
        return opt.isPresent() ? (String) opt.get() : null;
    }

    /**
     * 构造函数
     * @param preCacheKey
     * @param reactiveValueOperationsRepositoryImpl
     */
    public JwtTokenByRedis(String preCacheKey, ReactiveRepository reactiveValueOperationsRepositoryImpl) {
        this.PRE_CACHE_KEY = preCacheKey;
        this.reactiveValueOperationsRepositoryImpl = reactiveValueOperationsRepositoryImpl;
    }
}
