package com.sunny.maven.microservice.order.fegin.fallback;

import com.sunny.maven.microservice.bean.User;
import com.sunny.maven.microservice.order.fegin.UserService;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 用户服务容错类
 * @create: 2023-03-28 18:21
 */
@Component
public class UserServiceFallBack implements UserService {
    @Override
    public User getUser(Long uid) {
        User user = new User();
        user.setId(-1L);
        return user;
    }
}
