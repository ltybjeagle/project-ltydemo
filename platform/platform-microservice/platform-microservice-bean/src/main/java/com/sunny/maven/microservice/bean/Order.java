package com.sunny.maven.microservice.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.sunny.maven.microservice.utils.id.SnowFlakeFactory;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SUNNY
 * @description: 订单
 * @create: 2023-03-22 14:04
 */
@Data
@TableName(value = "t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 3732201798643495985L;
    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 用户id
     */
    @TableField("t_user_id")
    private Long userId;
    /**
     * 用户名
     */
    @TableField("t_user_name")
    private String username;
    /**
     * 手机号
     */
    @TableField("t_phone")
    private String phone;
    /**
     * 地址
     */
    @TableField("t_address")
    private String address;
    /**
     * 商品价格（总价）
     */
    @TableField("t_total_price")
    private BigDecimal totalPrice;

    public Order() {
        this.id = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
    }
}
