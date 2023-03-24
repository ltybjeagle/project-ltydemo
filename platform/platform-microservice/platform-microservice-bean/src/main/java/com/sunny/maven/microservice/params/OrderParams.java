package com.sunny.maven.microservice.params;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 订单
 * @create: 2023-03-23 12:32
 */
@NoArgsConstructor
@AllArgsConstructor
public class OrderParams implements Serializable {
    private static final long serialVersionUID = -2359571986205902118L;
    /**
     * 数量
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean isEmpty() {
        return count == 0;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
