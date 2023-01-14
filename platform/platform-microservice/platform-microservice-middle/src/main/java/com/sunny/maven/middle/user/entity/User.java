package com.sunny.maven.middle.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sunny.maven.middle.user.entity.enmu.UserType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 用户
 * @create: 2022-09-08 11:15
 */
@Data
@TableName("fasp_t_causer")
public class User implements Serializable {
    private static final long serialVersionUID = 2304637628675776365L;
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
     * password
     */
    @TableField("password")
    private String password;
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
     * division
     */
    @TableField("division")
    private String division;
    /**
     * pwoutdate
     */
    @TableField("pwoutdate")
    private String pwoutdate;
    /**
     * initialpassword
     */
    @TableField("initialpassword")
    private String initialpassword;
    /**
     * pwmoddate
     */
    @TableField("pwmoddate")
    private String pwmoddate;
    /**
     * userType
     */
    private UserType faspUserType;
    /**
     * loginmode
     */
    @TableField("loginmode")
    private String loginmode;
    /**
     * status
     */
    @TableField("status")
    private String status;
    /**
     * upagencyid
     */
    @TableField("upagencyid")
    private String upagencyid;
    /**
     * entrustguid
     */
    @TableField("entrustguid")
    private String entrustguid;
    /**
     * startdate
     */
    @TableField("startdate")
    private String startdate;
    /**
     * enddate
     */
    @TableField("enddate")
    private String enddate;
    /**
     * ak
     */
    @TableField("ak")
    private String ak;
    /**
     * issys
     */
    @TableField("issys")
    private String issys;
    /**
     * usertype
     */
    @TableField("usertype")
    private String usertype;
    /**
     * agency
     */
    @TableField("agency")
    private String agency;
    /**
     * idcode
     */
    @TableField("idcode")
    private String idcode;
    /**
     * phonenumber
     */
    @TableField("phonenumber")
    private String phonenumber;
    /**
     * email
     */
    @TableField("email")
    private String email;
    /**
     * remark
     */
    @TableField("remark")
    private String remark;
    /**
     * bank
     */
    @TableField("bank")
    private String bank;
    /**
     * ordernum
     */
    @TableField("ordernum")
    private String ordernum;
    /**
     * istown
     */
    @TableField("istown")
    private String istown;
    /**
     * admintype
     */
    @TableField("admintype")
    String admintype;
    /**
     * createdate
     */
    @TableField("createdate")
    String createdate;
    /**
     * updatedate
     */
    @TableField("updatedate")
    String updatedate;
    /**
     * overduedate
     */
    @TableField("overduedate")
    String overduedate;
    /**
     * logindate
     */
    @TableField("logindate")
    String logindate;
    /**
     * dbversion
     */
    @TableField("dbversion")
    String dbversion;
    /**
     * admdiv
     */
    @TableField("admdiv")
    String admdiv;
    /**
     * pinyin
     */
    @TableField("pinyin")
    String pinyin;

}
