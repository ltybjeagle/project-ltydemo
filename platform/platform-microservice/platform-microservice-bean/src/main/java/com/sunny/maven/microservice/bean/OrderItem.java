package com.sunny.maven.microservice.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.sunny.maven.microservice.utils.id.SnowFlakeFactory;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SUNNY
 * @description: 订单明细
 * @create: 2023-03-22 14:10
 */
@Data
@TableName(value = "t_order_item")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 566526832501069920L;
    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 订单ID
     */
    @TableField("t_order_id")
    private Long orderId;
    /**
     * 商品id
     */
    @TableField("t_pro_id")
    private Long proId;
    /**
     * 商品名称
     */
    @TableField("t_pro_name")
    private String proName;
    /**
     * 商品价格（单价）
     */
    @TableField("t_pro_price")
    private BigDecimal proPrice;
    /**
     * 购买数量
     */
    @TableField("t_number")
    private Integer number;

    public OrderItem() {
        this.id = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
    }
}
