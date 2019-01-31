package com.liuty.maven.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable {
    /**
     * 用户主键
     */
    private String guid;
    /**
     * 用户编码
     */
    private String code;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户状态
     */
    private int status;
    /**
     * 备注
     */
    private String remark;
}
