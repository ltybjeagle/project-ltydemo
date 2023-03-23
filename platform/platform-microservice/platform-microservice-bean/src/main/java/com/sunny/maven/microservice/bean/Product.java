package com.sunny.maven.microservice.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.sunny.maven.microservice.utils.id.SnowFlakeFactory;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SUNNY
 * @description: 商品
 * @create: 2023-03-22 13:57
 */
@Data
@TableName(value = "t_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 5837970747752906591L;
    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 商品名称
     */
    @TableField("t_pro_name")
    private String proName;
    /**
     * 商品价格
     */
    @TableField("t_pro_price")
    private BigDecimal proPrice;
    /**
     * 商品库存
     */
    @TableField("t_pro_stock")
    private Integer proStock;

    public Product() {
        this.id = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
    }
}
