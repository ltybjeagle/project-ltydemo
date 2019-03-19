package com.liuty.maven.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class UserEntity implements Serializable {
    /**
     * 用户主键
     */
    private int guid;
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
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private int age;
    /**
     * 电话
     */
    private String phone;
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
    /**
     * 创建时间
     */
    private Date create;
    /**
     * 更新时间
     */
    private Date modify;
    /**
     * 记录版本号
     */
    private int version;
}
