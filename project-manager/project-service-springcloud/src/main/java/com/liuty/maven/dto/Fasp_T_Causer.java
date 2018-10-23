package com.liuty.maven.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户信息
 * 注：类名、属性名跟数据库表、字段保持一致
 */
@Entity
public class Fasp_T_Causer {

    /**
     * 用户主键
     */
    @Id
    private String guid;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
