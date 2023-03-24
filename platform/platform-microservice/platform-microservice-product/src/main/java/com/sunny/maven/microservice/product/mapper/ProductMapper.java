package com.sunny.maven.microservice.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunny.maven.microservice.bean.Product;
import org.apache.ibatis.annotations.Param;

/**
 * @author SUNNY
 * @description: 商品服务Mapper接口
 * @create: 2023-03-23 11:26
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 扣减商品库存
     */
    int updateProductStockById(@Param("count") Integer count, @Param("id") Long id);
}
