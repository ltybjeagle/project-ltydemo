package com.sunny.maven.microservice.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunny.maven.microservice.order.service.OrderService;
import com.sunny.maven.microservice.order.service.SentinelService;
import com.sunny.maven.microservice.params.OrderParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 订单API
 * @create: 2023-03-23 14:03
 */
@Slf4j
@RestController
public class OrderController {

    private OrderService orderService;
    private SentinelService sentinelService;

    @GetMapping(value = "/submit_order")
    public String submitOrder(OrderParams orderParams) {
        log.info("提交订单时传递的参数:{}", JSONObject.toJSONString(orderParams));
        orderService.saveOrder(orderParams);
        return "success";
    }

    @GetMapping(value = "/current_request")
    public String currentRequest() {
        log.info("测试业务在高并发场景下是否存在问题");
        return "sunny";
    }

    @GetMapping(value = "/test_sentinel")
    public String testSentinel() {
        log.info("测试Sentinel");
        return "sentinel";
    }

    @GetMapping(value = "/test_sentinel4")
    public String testSentinel4() {
        log.info("测试Sentinel4");
        return "sentinel4";
    }

    @GetMapping(value = "/test_sentinel5")
    public String testSentinel5() {
        log.info("测试Sentinel5");
        return "sentinel5";
    }

    @GetMapping(value = "/test_sentinel6")
    public String testSentinel6() {
        log.info("测试Sentinel6");
        return sentinelService.sendMessage();
    }

    /**
     * 构造注入
     * @param orderService 订单处理
     */
    @Autowired
    public OrderController(@Qualifier("orderServiceV6") OrderService orderService,
                           @Qualifier("sentinelService") SentinelService sentinelService) {
        this.orderService = orderService;
        this.sentinelService = sentinelService;
    }
}
