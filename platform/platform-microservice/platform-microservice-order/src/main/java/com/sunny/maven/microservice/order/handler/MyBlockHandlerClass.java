package com.sunny.maven.microservice.order.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/6/8 16:26
 */
@Slf4j
public class MyBlockHandlerClass {

    public static String blockHandler(BlockException e) {
        log.error("限流了:{}", e.getMessage());
        return "限流了";
    }
}
