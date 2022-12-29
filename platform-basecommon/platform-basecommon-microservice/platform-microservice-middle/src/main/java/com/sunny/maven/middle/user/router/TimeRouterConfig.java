package com.sunny.maven.middle.user.router;

import com.sunny.maven.middle.user.handler.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author SUNNY
 * @description: 时间
 * @create: 2022-10-11 15:18
 */
@Configuration
public class TimeRouterConfig {

    private TimeHandler timeHandler;
    @Bean
    RouterFunction<ServerResponse> timeRouter() {
        return route(GET("/time"), req -> timeHandler.getTime(req)).
                andRoute(GET("/date"), timeHandler::getDate).
                andRoute(GET("/times"), timeHandler::sendTimePerSec);
    }

    @Autowired
    public TimeRouterConfig(TimeHandler timeHandler) {
        this.timeHandler = timeHandler;
    }
}
