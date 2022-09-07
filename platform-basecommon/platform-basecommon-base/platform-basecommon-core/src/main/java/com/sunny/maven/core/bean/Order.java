package com.sunny.maven.core.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.sunny.maven.core.utils.id.SnowFlakeFactory;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SUNNY
 * @description: 订单
 * @create: 2022-06-17 14:55
 */
@Data
@TableName("t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = -2907409980909070073L;
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

    public Order(){
        this.id = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
    }
}
