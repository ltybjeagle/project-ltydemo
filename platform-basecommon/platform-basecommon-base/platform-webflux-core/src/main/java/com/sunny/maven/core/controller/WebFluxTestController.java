package com.sunny.maven.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author SUNNY
 * @description: Mono测试
 * @create: 2022-09-29 11:16
 */
@Slf4j
@RestController
public class WebFluxTestController {

    @GetMapping("/one")
    private String getOne() {
        log.info("getOne start");
        String result = createStr();
        log.info("getOne end");
        return result;
    }

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return "some thing";
    }

    @GetMapping("/two")
    private Mono<String> getTwo() {
        log.info("getTwo start");
        Mono<String> result = Mono.fromSupplier(this::createStr);
        log.info("getTwo end");
        return result;
    }

    @GetMapping(value = "/three", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> getThree() {
        log.info("getThree start");
        Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return "flux data-" + i;
        }));
        log.info("getThree end");
        return result;
    }
}
