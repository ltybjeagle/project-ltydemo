package com.sunny.maven.microservice.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sunny.maven.microservice.bean.Order;
import com.sunny.maven.microservice.bean.OrderItem;
import com.sunny.maven.microservice.bean.Product;
import com.sunny.maven.microservice.bean.User;
import com.sunny.maven.microservice.order.mapper.OrderItemMapper;
import com.sunny.maven.microservice.order.mapper.OrderMapper;
import com.sunny.maven.microservice.order.service.OrderService;
import com.sunny.maven.microservice.params.OrderParams;
import com.sunny.maven.microservice.utils.constants.HttpCode;
import com.sunny.maven.microservice.utils.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author SUNNY
 * @description: 订单实现类
 * @create: 2023-03-23 12:35
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void saveOrder(OrderParams orderParams) {
        if (orderParams.isEmpty()) {
            throw new RuntimeException("参数异常: " + JSONObject.toJSONString(orderParams));
        }

        User user = restTemplate.getForObject("http://localhost:8060/user/get/" + orderParams.getUserId(),
                User.class);
        if (user == null) {
            throw new RuntimeException("未获取到用户信息: " + JSONObject.toJSONString(orderParams));
        }

        Product product = restTemplate.getForObject("http://localhost:8070/product/get/" +
                        orderParams.getProductId(), Product.class);
        if (product == null) {
            throw new RuntimeException("未获取到商品信息: " + JSONObject.toJSONString(orderParams));
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

        Result<Integer> result = restTemplate.getForObject("http://localhost:8070/product/update_count/" +
                orderParams.getProductId() + "/" + orderParams.getCount(), Result.class);

        if (result.getCode() != HttpCode.SUCCESS) {
            throw new RuntimeException("库存扣减失败");
        }
        log.info("库存扣减成功");
    }
}
