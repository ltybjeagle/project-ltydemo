package com.sunny.maven.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 用户角色
 * @create: 2022-09-08 15:32
 */
@Data
@TableName("fasp_t_carole")
public class Role implements Serializable {
    private static final long serialVersionUID = -5805902995800666476L;
    /**
     * agency
     */
    @TableField("agency")
    private String agency;
    /**
     * remark1
     */
    @TableField("remark1")
    private String remark1;
    /**
     * remark2
     */
    @TableField("remark2")
    private String remark2;
    /**
     * remark3
     */
    @TableField("remark3")
    private String remark3;
    /**
     * year
     */
    @TableField("year")
    private String year;
    /**
     * province
     */
    @TableField("province")
    private String province;
    /**
     * FaspRoleType
     */
    private Integer FaspRoleType;
    /**
     * roletype
     */
    @TableField("roletype")
    private Integer roletype;
    /**
     * rolenature
     */
    @TableField("rolenature")
    private Integer rolenature;
    /**
     * dbversion
     */
    @TableField("dbversion")
    private String dbversion;
    /**
     * status
     */
    @TableField("status")
    private String status;
    /**
     * division
     */
    @TableField("division")
    private String division;
    /**
     * bank
     */
    @TableField("bank")
    private String bank;
    /**
     * admdiv
     */
    @TableField("admdiv")
    private String admdiv;
    /**
     * rolelevel
     */
    @TableField("rolelevel")
    private String rolelevel;
    /**
     * guid
     */
    @TableId(value = "guid", type = IdType.INPUT)
    private String guid;
    /**
     * code
     */
    @TableField("code")
    private String code;
    /**
     * name
     */
    @TableField("name")
    private String name;
    /**
     * remark
     */
    @TableField("remark")
    private String remark;
    /**
     * issys
     */
    @TableField("issys")
    private Integer issys;
    /**
     * ordernum
     */
    @TableField("ordernum")
    private Integer ordernum;
    /**
     * paytypeflag
     */
    @TableField("paytypeflag")
    private String paytypeflag;
    /**
     * salarytypeflag
     */
    @TableField("salarytypeflag")
    private String salarytypeflag;
    /**
     * speaccttypeflag
     */
    @TableField("speaccttypeflag")
    private String speaccttypeflag;
    /**
     * bgttypeflag
     */
    @TableField("bgttypeflag")
    private String bgttypeflag;
}
