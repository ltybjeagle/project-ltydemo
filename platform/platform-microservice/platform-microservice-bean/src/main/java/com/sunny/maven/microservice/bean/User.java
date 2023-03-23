package com.sunny.maven.microservice.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.sunny.maven.microservice.utils.id.SnowFlakeFactory;
import com.sunny.maven.microservice.utils.password.PasswordUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 用户实体类
 * @create: 2023-03-22 12:28
 */
@Data
@TableName("t_user")
public class User implements Serializable {
    private static final long serialVersionUID = 5227716514539194070L;
    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 用户名
     */
    @TableField("t_username")
    private String username;
    /**
     * 密码
     */
    @TableField("t_password")
    private String password;
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

    public User() {
        this.id = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
        // 默认密码
        this.password = PasswordUtils.getPassWord("123456");
    }
}
