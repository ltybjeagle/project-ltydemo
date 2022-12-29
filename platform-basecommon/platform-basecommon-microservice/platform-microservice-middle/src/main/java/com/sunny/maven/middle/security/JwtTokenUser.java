package com.sunny.maven.middle.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 令牌用户
 * @create: 2022-09-07 15:44
 */
@JsonIgnoreProperties(value = {"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class JwtTokenUser implements UserDetails {
    @JSONField(serialize = false)
    private String username;
    @JSONField(serialize = false)
    private String password;
    @JSONField(serialize = false)
    private Integer state;
    @JSONField(serialize = false)
    private Collection<? extends GrantedAuthority> authorities;
    private String token;
    public JwtTokenUser() {
    }
    public JwtTokenUser(String username, String password, Integer state,
                        Collection<? extends GrantedAuthority> authorities, String token) {
        this.username = username;
        this.password = password;
        this.state = state;
        this.authorities = authorities;
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state == 1;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
