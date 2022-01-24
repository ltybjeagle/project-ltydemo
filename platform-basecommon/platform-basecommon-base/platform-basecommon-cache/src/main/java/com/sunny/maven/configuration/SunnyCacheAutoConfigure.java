package com.sunny.maven.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.sunny.maven.handler.AsyncRedisHandler;
import com.sunny.maven.runner.SunnyCacheRunner;
import com.sunny.maven.service.RedisCacheService;
import com.sunny.maven.template.RedisWithReentrantLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author SUNNY
 * @description: MyCache自动配置类
 * @create: 2021-11-24 10:51
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(SunnyCacheProperties.class)
@ConditionalOnProperty(prefix = "sunny.cache", value = "enabled", havingValue = "true")
public class SunnyCacheAutoConfigure implements AsyncConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(SunnyCacheAutoConfigure.class);

    @Resource
    private SunnyCacheProperties sunnyCacheProperties;
    /**
     * 缓存数据初始化
     * @return FaspCacheRunner
     */
    @Bean
    public SunnyCacheRunner sunnyCacheRunner() {
        return new SunnyCacheRunner();
    }

    /**
     * 缓存对象初始化
     * @return SunnyCacheBeanProcessor
     */
    @Bean
    public SunnyCacheBeanProcessor sunnyCacheBeanProcessor() {
        return new SunnyCacheBeanProcessor();
    }

    /**
     * Redis缓存工具
     * @param redisTemplate Redis模板对象
     * @param valueOperations redis字符串操作
     * @param hashOperations redis Hash操作
     * @return RedisCacheService
     */
    @Bean
    public RedisCacheService redisCacheService(RedisTemplate<String, Object> redisTemplate,
                                               ValueOperations<String, Object> valueOperations,
                                               HashOperations<String, String, Object> hashOperations) {
        return new RedisCacheService(redisTemplate, valueOperations, hashOperations);
    }

    /**
     * 分布式锁对象
     * @param redisTemplate Redis模板对象
     * @return RedisWithReentrantLock
     */
    @Bean
    public RedisWithReentrantLock redisWithReentrantLock(RedisTemplate<String, Object> redisTemplate) {
        return new RedisWithReentrantLock(redisTemplate);
    }

    /**
     * redis字符串操作
     * @param redisTemplate 模板对象
     * @return ValueOperations
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * redis Hash操作
     * @param redisTemplate 模板对象
     * @return HashOperations
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 设置Redis模板对象
     * @param redisConnectionFactory 连接工厂
     * @return RedisTemplate
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 指定redis模板
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(jackson2JsonRedisSerializer());
        template.setKeySerializer(jackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 设置Redis连接工厂
     * @param poolConfig 连接池
     * @return JedisConnectionFactory
     */
    @Bean(name = "redisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig) {
        JedisConnectionFactory factory;
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)
                        JedisClientConfiguration.builder()
                                .connectTimeout(Duration.ofMillis(sunnyCacheProperties.getCacheTimeout()));
        jpcf.poolConfig(poolConfig);
        String master, nodes;
        if (StringUtils.isNotEmpty(master = sunnyCacheProperties.getCacheMaster()) &&
                StringUtils.isNotEmpty(nodes = sunnyCacheProperties.getCacheNodes())) {
            /*
             * 主从模式：哨兵监听主从切换
             */
            Set<String> sentinels = new LinkedHashSet<>();
            Collections.addAll(sentinels, nodes.split(","));
            RedisSentinelConfiguration sentinelConfiguration =
                    new RedisSentinelConfiguration(master, sentinels);
            sentinelConfiguration.setDatabase(sunnyCacheProperties.getCacheDatabase());

            factory = new JedisConnectionFactory(sentinelConfiguration, jpcf.build());
        } else {
            /*
             * 单机模式
             */
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(sunnyCacheProperties.getCacheHost());
            redisStandaloneConfiguration.setPort(sunnyCacheProperties.getCachePort());
            redisStandaloneConfiguration.setDatabase(sunnyCacheProperties.getCacheDatabase());
            redisStandaloneConfiguration.setPassword(RedisPassword.of(sunnyCacheProperties.getCachePwd()));

            factory = new JedisConnectionFactory(redisStandaloneConfiguration, jpcf.build());
        }
        return factory;
    }

    /**
     * 设置Redis连接池
     * @return JedisPoolConfig
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数
        jedisPoolConfig.setMaxTotal(sunnyCacheProperties.getCacheMaxActive());
        // 最大空闲连接数
        jedisPoolConfig.setMaxIdle(sunnyCacheProperties.getCacheMaxIdle());
        // 最小空闲连接数
        jedisPoolConfig.setMinIdle(sunnyCacheProperties.getCacheMinIdle());
        // 当池内没有可用连接时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(sunnyCacheProperties.getCacheMaxWait());
        // 其他属性可以自行添加
        return jedisPoolConfig;
    }

    /**
     * 设置CacheManager的值序列化方式为json序列化
     * @return Jackson2JsonRedisSerializer
     */
    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        // 设置CacheManager的值序列化方式为json序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 设置缓存执行线程池
     * @return Executor
     */
    @Override
    @Bean(name = "cacheExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(sunnyCacheProperties.getAsyncCache().getCorePoolSize());
        executor.setMaxPoolSize(sunnyCacheProperties.getAsyncCache().getMaxPoolSize());
        executor.setQueueCapacity(sunnyCacheProperties.getAsyncCache().getQueueCapacity());
        executor.setKeepAliveSeconds(sunnyCacheProperties.getAsyncCache().getKeepAliveSeconds());
        executor.setThreadNamePrefix(sunnyCacheProperties.getAsyncCache().getThreadNamePrefix());
        // 设置队列满的情况下，直接使用主线程执行任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return new AsyncRedisHandler(executor);
    }

    /**
     * 异步任务中异常处理
     * @return AsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new SimpleAsyncUncaughtExceptionHandler();
        return (throwable, method, objects) -> {
            logger.error("======================={}=================={}", throwable.getMessage(), throwable);
            logger.error("exception method: {}", method.getName());
        };
    }
}
