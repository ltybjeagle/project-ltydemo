package com.sunny.maven.microservice.order.service;

import com.sunny.maven.microservice.params.OrderParams;

/**
 * @author SUNNY
 * @description: 订单业务接口
 * @create: 2023-03-23 12:18
 */
public interface OrderService {
    /**
     * 保存订单
     */
    void saveOrder(OrderParams orderParams);
}
