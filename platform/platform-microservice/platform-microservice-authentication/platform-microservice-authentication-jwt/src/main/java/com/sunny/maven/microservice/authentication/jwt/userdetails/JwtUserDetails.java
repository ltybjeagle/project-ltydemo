package com.sunny.maven.microservice.authentication.jwt.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 令牌用户
 * @create: 2023-04-02 11:21
 */
public class JwtUserDetails implements UserDetails {
    private String username;
    private String password;
    private Integer state;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails() {
    }

    public JwtUserDetails(String username, String password, Integer state,
                          Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.state = state;
        this.authorities = authorities;
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
        return true;
    }
}
