package com.sunny.maven.microservice.order.fegin.fallback;

import com.sunny.maven.microservice.bean.Product;
import com.sunny.maven.microservice.order.fegin.ProductService;
import com.sunny.maven.microservice.utils.resp.Result;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 商品微服务的容错类
 * @create: 2023-03-30 15:22
 */
@Component
public class ProductServiceFallBack implements ProductService {
    @Override
    public Product getProduct(Long pid) {
        Product product = new Product();
        product.setId(-1L);
        return product;
    }

    @Override
    public Result<Integer> updateCount(Long pid, Integer count) {
        Result<Integer> result = new Result<>();
        result.setCode(1001);
        result.setCodeMsg("触发了容错逻辑");
        return result;
    }
}
