package com.sunny.maven.middle.redis.configuration;

import com.sunny.maven.cache.configuration.CacheProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author SUNNY
 * @description: Redis配置类
 * @create: 2022-10-17 15:35
 */
@Configuration
@EnableAutoConfiguration(exclude = {RedisReactiveAutoConfiguration.class})
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "platform.cache", value = "enabled", havingValue = "true")
public class RedisConfig {

    /**
     * 字符串存储
     * @param lettuceConnectionFactory 工厂
     * @return ReactiveRedisTemplate
     */
    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory lettuceConnectionFactory) {
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, RedisSerializationContext.string());
    }

    /**
     * redis字符串操作
     * @param reactiveRedisOperations 模板对象
     * @return ValueOperations
     */
    @Bean
    public ReactiveValueOperations<String, Object> reactiveValueOperations(
            ReactiveRedisTemplate<String, Object> reactiveRedisOperations) {
        return reactiveRedisOperations.opsForValue();
    }

    /**
     * Jackson2序列化存储
     * @param lettuceConnectionFactory 工厂
     * @return ReactiveRedisTemplate
     */
    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisOperations(
            ReactiveRedisConnectionFactory lettuceConnectionFactory) {
        // 设置CacheManager的值序列化方式为json序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, Object> context = builder.value(jackson2JsonRedisSerializer).build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, context);
    }

    @Resource
    private CacheProperties cacheProperties;

    /**
     * Redis工厂
     * @return ReactiveRedisConnectionFactory
     */
    @Bean
    public ReactiveRedisConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(cacheProperties.getCacheHost());
        redisStandaloneConfiguration.setPort(cacheProperties.getCachePort());
        redisStandaloneConfiguration.setDatabase(cacheProperties.getCacheDatabase());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(cacheProperties.getCachePwd()));
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle (cacheProperties.getCacheMaxIdle());
        poolConfig.setMinIdle (cacheProperties.getCacheMinIdle());
        poolConfig.setMaxWait(Duration.ofMillis(cacheProperties.getCacheMaxWait()));
        poolConfig.setMaxTotal (cacheProperties.getCacheMaxActive());
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration =
                LettucePoolingClientConfiguration.builder ().
                        commandTimeout (Duration.ofMillis(cacheProperties.getCacheTimeout())).
                        shutdownTimeout (Duration.ZERO).poolConfig (poolConfig).build ();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettucePoolingClientConfiguration);
        lettuceConnectionFactory.setShareNativeConnection(false);
        return lettuceConnectionFactory;
    }
}
