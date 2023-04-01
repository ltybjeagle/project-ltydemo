package com.sunny.maven.microservice.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sunny.maven.microservice.bean.Order;
import com.sunny.maven.microservice.bean.OrderItem;
import com.sunny.maven.microservice.bean.Product;
import com.sunny.maven.microservice.bean.User;
import com.sunny.maven.microservice.order.fegin.ProductService;
import com.sunny.maven.microservice.order.fegin.UserService;
import com.sunny.maven.microservice.order.mapper.OrderItemMapper;
import com.sunny.maven.microservice.order.mapper.OrderMapper;
import com.sunny.maven.microservice.order.service.OrderService;
import com.sunny.maven.microservice.params.OrderParams;
import com.sunny.maven.microservice.utils.constants.HttpCode;
import com.sunny.maven.microservice.utils.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author SUNNY
 * @description: 订单实现类
 * @create: 2023-03-23 12:35
 */
@Slf4j
@Service("orderServiceV6")
public class OrderServiceV6Impl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    public void saveOrder(OrderParams orderParams) {
        log.info("orderServiceV5 execute");
        if (orderParams.isEmpty()) {
            throw new RuntimeException("参数异常: " + JSONObject.toJSONString(orderParams));
        }

        User user = userService.getUser(orderParams.getUserId());
        if (user == null) {
            throw new RuntimeException("未获取到用户信息: " + JSONObject.toJSONString(orderParams));
        }

        if (user.getId() == -1) {
            throw new RuntimeException("触发了用户微服务的容错逻辑: " + JSONObject.toJSONString(orderParams));
        }

        Product product = productService.getProduct(orderParams.getProductId());
        if (product == null) {
            throw new RuntimeException("未获取到商品信息: " + JSONObject.toJSONString(orderParams));
        }

        if (product.getId() == -1) {
            throw new RuntimeException("触发了商品微服务的容错逻辑: " + JSONObject.toJSONString(orderParams));
        }

        if (orderParams.getCount() > product.getProStock()) {
            throw new RuntimeException("商品库存不足: " + JSONObject.toJSONString(orderParams));
        }

        Order order = new Order();
        order.setAddress(user.getAddress());
        order.setPhone(user.getPhone());
        order.setUserId(user.getId());
        order.setUsername(user.getUsername());
        order.setTotalPrice(product.getProPrice().multiply(BigDecimal.valueOf(orderParams.getCount())));
        orderMapper.insert(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(orderParams.getCount());
        orderItem.setOrderId(order.getId());
        orderItem.setProId(product.getId());
        orderItem.setProName(product.getProName());
        orderItem.setProPrice(product.getProPrice());
        orderItemMapper.insert(orderItem);

        Result<Integer> result = productService.updateCount(orderParams.getProductId(), orderParams.getCount());
        if (result.getCode() == 1001) {
            throw new RuntimeException("触发了商品微服务的容错逻辑: " + JSONObject.toJSONString(orderParams));
        }
        if (result.getCode() != HttpCode.SUCCESS) {
            throw new RuntimeException("库存扣减失败");
        }
        log.info("库存扣减成功");
    }
}
