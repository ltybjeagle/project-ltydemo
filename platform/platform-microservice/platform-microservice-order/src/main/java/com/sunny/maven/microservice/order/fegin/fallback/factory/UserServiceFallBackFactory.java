package com.sunny.maven.microservice.order.fegin.fallback.factory;

import com.sunny.maven.microservice.bean.User;
import com.sunny.maven.microservice.order.fegin.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 用户微服务容错Factory
 * @create: 2023-03-31 11:16
 */
@Slf4j
@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable cause) {
        log.error("UserServiceFallBackFactory == >> {}", cause.getMessage());
        return uid -> {
            User user = new User();
            user.setId(-1L);
            return user;
        };
    }
}
