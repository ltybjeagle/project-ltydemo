package com.sunny.maven.microservice.params;

import lombok.Data;

/**
 * @author SUNNY
 * @description: 订单
 * @create: 2023-03-23 12:32
 */
@Data
public class OrderParams {
    /**
     * 购买的商品数量
     */
    private Integer count;

    /**
     * 商品
     */
    private Long productId;

    /**
     * 用户
     */
    private Long userId;

    public boolean isEmpty() {
        return (productId == null || productId <= 0) ||
                (userId == null || productId <= 0) ||
                (count == null || count <= 0);
    }
}
