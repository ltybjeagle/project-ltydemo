package com.sunny.maven.middle.user.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author SUNNY
 * @description: 时间
 * @create: 2022-10-11 14:59
 */
@Component
public class TimeHandler {
    /**
     * 获取时间
     * @param request
     * @return
     */
    public Mono<ServerResponse> getTime(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just(
                String.format("Now is %s", LocalTime.now().format(DateTimeFormatter.ISO_TIME))), String.class);
    }

    /**
     * 获取日期
     * @param request
     * @return
     */
    public Mono<ServerResponse> getDate(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just(
                String.format("Now is %s", LocalDate.now().format(DateTimeFormatter.ISO_DATE))), String.class);
    }

    /**
     * 推送时间（每秒）
     * @param request
     * @return
     */
    public Mono<ServerResponse> sendTimePerSec(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1)).
                map(l -> String.format("Now is %s", LocalTime.now().format(DateTimeFormatter.ISO_TIME))), String.class);
    }
}
