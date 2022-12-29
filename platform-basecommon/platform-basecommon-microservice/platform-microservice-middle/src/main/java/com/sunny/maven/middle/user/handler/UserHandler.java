package com.sunny.maven.middle.user.handler;

import com.sunny.maven.middle.user.entity.User;
import com.sunny.maven.middle.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author SUNNY
 * @description: 用户handler
 * @create: 2022-09-29 16:55
 */
@Component
public class UserHandler {
    private UserService userService;

    /**
     * 获取所有用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUsers(ServerRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        return ok().body(userService.getUsers(), User.class);
    }

    /**
     * 根据ID查找
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok().body(userService.getUserById(id), User.class);
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return ok().body(userService.createOrUpdateUser(user), User.class);
    }

    /**
     * 根据ID删除
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok().body(userService.deleteUser(id), User.class);
    }

    /**
     * 构造
     * @param userService
     */
    public UserHandler(UserService userService) {
        this.userService = userService;
    }
}
