package com.sunny.maven.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * @author SUNNY
 * @description: 会话配置
 * @create: 2022-02-10 22:35
 */
@EnableRedisHttpSession
public class Oauth2HttpSessionConfig {

//    /**
//     * 提供redis连接，默认localhost:6379
//     * @return RedisConnectionFactory
//     */
//    @Bean
//    public RedisConnectionFactory connectionFactory() {
//        return new JedisConnectionFactory();
//    }

    private FindByIndexNameSessionRepository sessionRepository;

    /**
     * SpringSessionBackedSessionRegistry是 session 为 spring security 提供的用于集群环境下控制会话并发的
     * 会话注册表实现类
     * @return SpringSessionBackedSessionRegistry
     */
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

//    /**
//     * HttpSession的事件监听，改用session提供的会话注册表
//     * @return
//     */
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

    /**
     * 构造注入
     * @param sessionRepository
     */
    @Autowired
    public Oauth2HttpSessionConfig(FindByIndexNameSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
}
