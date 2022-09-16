package com.sunny.maven.user.security;

import com.sunny.maven.user.entity.Role;
import com.sunny.maven.user.entity.User;
import com.sunny.maven.user.mapper.RoleMapper;
import com.sunny.maven.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SUNNY
 * @description: 用户查询服务
 * @create: 2022-09-08 10:40
 */
@Service(value = "userDetailsService")
public class JwtTokenUserDetailsServiceImpl implements UserDetailsService {
    private UserMapper userMapper;
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUserCode(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        }
        List<Role> roles = roleMapper.selectRolesByUserId(user.getGuid());
        List<SimpleGrantedAuthority> collect = roles.stream().
                map(Role::getGuid).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new JwtTokenUser(user.getCode(), user.getPassword(), Integer.valueOf(user.getStatus()), collect);
    }

    /**
     * 构造注入
     * @param userMapper
     * @param roleMapper
     */
    @Autowired
    public JwtTokenUserDetailsServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }
}
