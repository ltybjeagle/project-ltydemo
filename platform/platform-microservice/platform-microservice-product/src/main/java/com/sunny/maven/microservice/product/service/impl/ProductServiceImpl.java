package com.sunny.maven.microservice.product.service.impl;

import com.sunny.maven.microservice.bean.Product;
import com.sunny.maven.microservice.product.mapper.ProductMapper;
import com.sunny.maven.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 商品业务实现类
 * @create: 2023-03-23 11:50
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public Product getProductById(Long pid) {
        return productMapper.selectById(pid);
    }

    @Override
    public int updateProductStockById(Integer count, Long id) {
        return productMapper.updateProductStockById(count, id);
    }
}
