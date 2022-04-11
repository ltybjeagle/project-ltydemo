package com.sunny.maven.cache.service.redis;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * @author SUNNY
 * @description: 限流类
 * @create: 2022-03-01 22:45
 */
public class AccessLimiter {

    private static final Logger logger = LoggerFactory.getLogger(AccessLimiter.class);

    /**
     * Redis模板对象
     */
    private RedisTemplate<String, Object> redisTemplate;
    private RedisScript<Boolean> rateLimitLua;

    /**
     * 获取令牌
     * @param key
     * @param limit
     */
    public void limitAccess(String key, Integer limit){
        boolean acquired = redisTemplate.execute(
                // lua脚本的真身
                rateLimitLua,
                // lua脚本中的key列表
                Lists.newArrayList(key),
                // lua脚本的value列表
                limit.toString()
        );

        if (!acquired) {
            logger.error("Your access is blocked, key={}", key);
            throw new RuntimeException("Your access is blocked");
        }
    }

    /**
     * 构造函数
     * @param redisTemplate
     * @param rateLimitLua
     */
    public AccessLimiter(RedisTemplate<String, Object> redisTemplate, RedisScript<Boolean> rateLimitLua) {
        this.redisTemplate = redisTemplate;
        this.rateLimitLua = rateLimitLua;
    }
}
