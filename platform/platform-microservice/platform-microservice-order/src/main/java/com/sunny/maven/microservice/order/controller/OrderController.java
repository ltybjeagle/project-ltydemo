package com.sunny.maven.microservice.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunny.maven.microservice.order.service.OrderService;
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

    @GetMapping(value = "/submit_order")
    public String submitOrder(OrderParams orderParams) {
        log.info("提交订单时传递的参数:{}", JSONObject.toJSONString(orderParams));
        orderService.saveOrder(orderParams);
        return "success";
    }

    /**
     * 构造注入
     * @param orderService 订单处理
     */
    @Autowired
    public OrderController(@Qualifier("orderServiceV5") OrderService orderService) {
        this.orderService = orderService;
    }
}
