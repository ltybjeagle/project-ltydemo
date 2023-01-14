package com.sunny.maven.middle.user.router;

import com.sunny.maven.middle.user.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author SUNNY
 * @description: 用户Router配置
 * @create: 2022-09-29 17:08
 */
@Configuration
public class UserRouterConfig {
    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return RouterFunctions.nest(
                // 相当于类上面的@RequestMapping
                RequestPredicates.path("/user"),
                RouterFunctions.
                        route(RequestPredicates.GET("/all").
                                        and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                userHandler::getUsers).
                        andRoute(RequestPredicates.GET("/{id}").
                                        and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                userHandler::getUserById).
                        andRoute(RequestPredicates.POST("/save").
                                        and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                userHandler::createUser).
                        andRoute(RequestPredicates.DELETE("/{id}").
                                and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        userHandler::deleteUser)
        );
    }
}
