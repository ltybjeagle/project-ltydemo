package com.liuty.maven.cache.redis;

import com.liuty.maven.cache.CacheEnvProperty;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/23
 */
public class RedisCacheCreate {

    private String database = CacheEnvProperty.getProperties("spring.redis.database");
    private String host = CacheEnvProperty.getProperties("spring.redis.host");
    private String port = CacheEnvProperty.getProperties("spring.redis.port");
    private String password = CacheEnvProperty.getProperties("spring.redis.password");
    private String timeout = CacheEnvProperty.getProperties("spring.redis.timeout");

    private String maxActive = CacheEnvProperty.getProperties("spring.redis.lettuce.pool.max-active");
    private String maxIdle = CacheEnvProperty.getProperties("spring.redis.lettuce.pool.max-idle");
    private String minIdle = CacheEnvProperty.getProperties("spring.redis.lettuce.pool.max-wait");
    private String maxWait = CacheEnvProperty.getProperties("spring.redis.lettuce.pool.min-idle");
    private LettuceConnectionFactory factory;
    private RedisSerializer<String> redisKeySerializer = new StringRedisSerializer();
    private RedisSerializer<Object> redisValueSerializer = new GenericJackson2JsonRedisSerializer();

    public static RedisCacheCreate getInstance() {
        return RedisCacheCreateInstance.REDIS_CACHE_CREATE;
    }

    public LettuceConnectionFactory getLettuceConnectionFactory() {
        return this.factory;
    }

    /**
     * 获取key值编解码器
     * @return
     */
    public RedisSerializer getRedisKeySerializer() {
        return this.redisKeySerializer;
    }

    /**
     * 获取Value值编解码器
     * @return
     */
    public RedisSerializer getRedisValueSerializer() {
        return this.redisValueSerializer;
    }

    private RedisCacheCreate() {
        /*
         * 单实例模式
         */
        database = database == null ? "0" : database;
        host = host == null ? "127.0.0.1" : host;
        port = port == null ? "6379" : port;
        password = password == null ? "" : password;
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(Integer.parseInt(database));
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        /*
         * 集群模式
        String clusterNodes = "";
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String[] serverArray = clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for (String ipPort : serverArray) {
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0], Integer.parseInt(ipAndPort[1])));
        }
        redisClusterConfiguration.setPassword(RedisPassword.of(password));
        redisClusterConfiguration.setClusterNodes(nodes);
        */
        maxActive = maxActive == null ? "8" : maxActive;
        maxIdle = maxIdle == null ? "8" : maxIdle;
        minIdle = minIdle == null ? "0" : minIdle;
        maxWait = maxWait == null ? "-1" : maxWait;
        timeout = timeout == null ? "10000" : timeout;
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(maxActive));
        poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        poolConfig.setMinIdle(Integer.parseInt(minIdle));
        poolConfig.setMaxWaitMillis(Long.parseLong(substring(maxWait)));

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMinutes(Integer.parseInt(substring(timeout))))
                .poolConfig(poolConfig).build();
        factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }

    private static class RedisCacheCreateInstance {
        private static final RedisCacheCreate REDIS_CACHE_CREATE = new RedisCacheCreate();
    }

    private String substring(String str) {
        int index = str.indexOf("ms");
        if (index > -1) {
            str = str.substring(0, index);
        }
        return str;
    }
}
