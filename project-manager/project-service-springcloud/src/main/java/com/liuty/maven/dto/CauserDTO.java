package com.liuty.maven.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * 类注解说明：
 *      1、注解@Entity：标注类是实体类，与数据库表映射
 *      2、注解@Data：增加吃注解，IDE自动维护get/set方法
 *
 * @Description: 用户信息实体组件，类名、属性名跟数据库表、字段保持一致
 */
@Entity
@Table(name = "t_causer")
@Data
public class CauserDTO {

    /**
     * 用户主键
     */
    @Id
    private int guid;
    /**
     * 用户编码
     */
    @Column
    private String code;
    /**
     * 用户密码
     */
    @Column
    private String password;
    /**
     * 用户名称
     */
    @Column
    private String name;
    /**
     * 用户性别
     */
    @Column
    private String sex;
    /**
     * 用户年龄
     */
    @Column
    private int age;
    /**
     * 用户电话号码
     */
    @Column
    private String phone;
    /**
     * 用户邮箱
     */
    @Column
    private String email;
    /**
     * 用户状态
     */
    @Column
    private int status;
    /**
     * 备注
     */
    @Column
    private String remark;
    /**
     * 记录新增时间
     */
    @Column
    private Date createTime;
    /**
     * 记录更新时间
     */
    @Column
    private Date modifyTime;
    /**
     * 记录版本号
     */
    @Column
    private int version;
}
