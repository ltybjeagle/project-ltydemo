package com.sunny.maven.microservice.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sunny.maven.microservice.order.handler.MyBlockHandlerClass;
import com.sunny.maven.microservice.order.handler.MyFallbackClass;
import com.sunny.maven.microservice.order.service.SentinelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: SentinelServiceImpl
 * @create: 2023-04-01 10:38
 */
@Slf4j
@Service("sentinelService")
public class SentinelServiceImpl implements SentinelService {
    private int count = 0;


    @Override
    @SentinelResource(
            value = "sendMessage",
            blockHandlerClass = MyBlockHandlerClass.class,
            blockHandler = "blockHandler",
            fallbackClass = MyFallbackClass.class,
            fallback = "fallback"
    )
    public String sendMessage() {
        count++;
        // 25%的异常率
        if (count % 4 == 0) {
            throw new RuntimeException("25%的异常率");
        }
        return "sendMessage";
//        System.out.println("测试Sentinel的链路流控模式");
    }

    public String blockHandler(BlockException e) {
        log.error("限流了:{}", e.getMessage());
        return "限流了";
    }

    public String fallback(Throwable e) {
        log.error("异常了:{}", e.getMessage());
        return "异常了";
    }
}
