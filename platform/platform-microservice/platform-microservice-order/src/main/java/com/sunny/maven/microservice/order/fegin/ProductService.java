package com.sunny.maven.microservice.order.fegin;

import com.sunny.maven.microservice.bean.Product;
import com.sunny.maven.microservice.order.fegin.fallback.ProductServiceFallBack;
import com.sunny.maven.microservice.order.fegin.fallback.factory.ProductServiceFallBackFactory;
import com.sunny.maven.microservice.utils.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author SUNNY
 * @description: 调用商品微服务的接口
 * @create: 2023-03-24 16:06
 */
//@FeignClient(value = "microservice-product", fallback = ProductServiceFallBack.class)
@FeignClient(value = "microservice-product", fallbackFactory = ProductServiceFallBackFactory.class)
public interface ProductService {
    /**
     * 获取商品信息
     */
    @GetMapping(value = "/product/get/{pid}")
    Product getProduct(@PathVariable("pid") Long pid);

    /**
     * 更新库存数量
     */
    @GetMapping(value = "/product/update_count/{pid}/{count}")
    Result<Integer> updateCount(@PathVariable("pid") Long pid, @PathVariable("count") Integer count);
}
