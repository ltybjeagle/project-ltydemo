package com.sunny.maven.cache.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sunny.maven.cache.deserializer.SimpleGrantedAuthorityDeserializer;
import com.sunny.maven.cache.service.redis.CacheRedisService;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author SUNNY
 * @description: Cache自动配置类
 * @create: 2022-09-19 18:11
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "platform.cache", value = "enabled", havingValue = "true")
public class CacheAutoConfigure {

    @Resource
    private CacheProperties cacheProperties;

    @Bean
    public CacheRedisService cacheRedisService(RedisTemplate<String, Object> redisTemplate,
                                               ValueOperations<String, Object> valueOperations) {
        return new CacheRedisService(redisTemplate, valueOperations);
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
                        JedisClientConfiguration.builder().
                                connectTimeout(Duration.ofMillis(cacheProperties.getCacheTimeout())).
                                readTimeout(Duration.ofMillis(cacheProperties.getCacheTimeout()));
        jpcf.poolConfig(poolConfig);
        String master, nodes;
        if (StringUtils.isNotEmpty(master = cacheProperties.getCacheMaster()) &&
                StringUtils.isNotEmpty(nodes = cacheProperties.getCacheNodes())) {
            /*
             * 主从模式：哨兵监听主从切换
             */
            Set<String> sentinels = new LinkedHashSet<>();
            Collections.addAll(sentinels, nodes.split(","));
            RedisSentinelConfiguration sentinelConfiguration =
                    new RedisSentinelConfiguration(master, sentinels);
            sentinelConfiguration.setDatabase(cacheProperties.getCacheDatabase());

            factory = new JedisConnectionFactory(sentinelConfiguration, jpcf.build());
        } else {
            /*
             * 单机模式
             */
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(cacheProperties.getCacheHost());
            redisStandaloneConfiguration.setPort(cacheProperties.getCachePort());
            redisStandaloneConfiguration.setDatabase(cacheProperties.getCacheDatabase());
            redisStandaloneConfiguration.setPassword(RedisPassword.of(cacheProperties.getCachePwd()));

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
        jedisPoolConfig.setMaxTotal(cacheProperties.getCacheMaxActive());
        // 最大空闲连接数
        jedisPoolConfig.setMaxIdle(cacheProperties.getCacheMaxIdle());
        // 最小空闲连接数
        jedisPoolConfig.setMinIdle(cacheProperties.getCacheMinIdle());
        // 当池内没有可用连接时，最大等待时间
        jedisPoolConfig.setMaxWait(Duration.ofMillis(cacheProperties.getCacheMaxWait()));
        // 其他属性可以自行添加
        return jedisPoolConfig;
    }

    /**
     * 设置CacheManager的值序列化方式为json序列化
     * @return Jackson2JsonRedisSerializer
     */
    @Bean
    public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        // 设置CacheManager的值序列化方式为json序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        // 解决jackson2无法反序列化SimpleGrantedAuthority问题
        om.registerModule(new SimpleModule().addDeserializer(SimpleGrantedAuthority.class,
                new SimpleGrantedAuthorityDeserializer()));
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }
}
