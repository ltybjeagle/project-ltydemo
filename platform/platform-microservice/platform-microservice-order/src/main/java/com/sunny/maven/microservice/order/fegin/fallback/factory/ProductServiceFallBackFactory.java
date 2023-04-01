package com.sunny.maven.microservice.order.fegin.fallback.factory;

import com.sunny.maven.microservice.bean.Product;
import com.sunny.maven.microservice.order.fegin.ProductService;
import com.sunny.maven.microservice.utils.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 商品微服务容错Factory
 * @create: 2023-03-31 11:25
 */
@Slf4j
@Component
public class ProductServiceFallBackFactory implements FallbackFactory<ProductService> {
    @Override
    public ProductService create(Throwable cause) {
        log.error("ProductServiceFallBackFactory == >> {}", cause.getMessage());
        return new ProductService() {
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
        };
    }
}
