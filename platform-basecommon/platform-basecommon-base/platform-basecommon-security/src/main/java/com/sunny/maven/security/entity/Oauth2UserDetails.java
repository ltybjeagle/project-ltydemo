package com.sunny.maven.security.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 用户认证实体
 * @create: 2022-02-04 16:09
 */
public class Oauth2UserDetails implements UserDetails {
    private static final long serialVersionUID = 753621995742873533L;
    private static final String ENABLE = "1";
    private Collection<GrantedAuthority> authorities;
    /**
     * 主键
     */
    private String guid;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态
     */
    private String status;

    /**
     * 构造函数
     * @param guid 主键
     * @param name 名称
     * @param code 编码
     * @param password 密码
     * @param status 状态
     */
    public Oauth2UserDetails(String guid, String name, String code, String password, String status) {
        this.setGuid(guid);
        this.setName(name);
        this.setCode(code);
        this.setPassword(password);
        this.setStatus(status);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(this.status, ENABLE);
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Oauth2UserDetails && this.code.equals(((Oauth2UserDetails) obj).code);
    }
    @Override
    public int hashCode() {
        return this.code.hashCode();
    }
}
