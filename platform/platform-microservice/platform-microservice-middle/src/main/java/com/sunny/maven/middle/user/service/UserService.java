package com.sunny.maven.middle.user.service;

import com.sunny.maven.middle.user.entity.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUNNY
 * @description: 用户业务服务
 * @create: 2022-10-14 10:01
 */
@Service
public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    /**
     * 获取所有用户数据
     * @return
     */
    public Flux<User> getUsers() {
        return Flux.fromIterable(this.users.values());
    }

    /**
     * 根据主键查询用户数据
     * @param ids
     * @return
     */
    public Flux<User> getUsersByIds(final Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.users.get(id)));
    }

    /**
     * 根据主键查询用户数据
     * @param id
     * @return
     */
    public Mono<User> getUserById(final String id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    /**
     * 创建/更新用户信息
     * @param userMono
     * @return
     */
    public Mono<Void> createOrUpdateUser(final Mono<User> userMono) {
        return userMono.doOnNext(user -> {
            users.put(user.getGuid(), user);
        }).thenEmpty(Mono.empty());
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    public Mono<User> deleteUser(final String id) {
        return Mono.justOrEmpty(this.users.remove(id));
    }
}
