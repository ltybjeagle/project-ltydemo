package com.sunny.maven.microservice.product.service;

import com.sunny.maven.microservice.bean.Product;

/**
 * @author SUNNY
 * @description: 商品Service接口
 * @create: 2023-03-23 11:39
 */
public interface ProductService {

    /**
     * 根据商品id获取商品信息
     */
    Product getProductById(Long pid);
    /**
     * 扣减商品库存
     */
    int updateProductStockById(Integer count, Long id);
}
