package com.sunny.maven.middle.security;

import com.sunny.maven.core.utils.crypto.Base64Utils;
import com.sunny.maven.core.utils.id.SnowFlake;
import com.sunny.maven.core.utils.id.SnowFlakeFactory;
import com.sunny.maven.middle.user.entity.Role;
import com.sunny.maven.middle.user.entity.User;
import com.sunny.maven.middle.user.mapper.RoleMapper;
import com.sunny.maven.middle.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SUNNY
 * @description: 用户信息查询与登录状态保存
 * @create: 2022-11-02 17:37
 */
@Service(value = "userDetailsService")
public class JwtTokenUserDetailsServiceImpl implements ReactiveUserDetailsService {
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private SnowFlake snowFlake;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        User user = userMapper.selectByUserCode(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        }
        List<Role> roles = roleMapper.selectRolesByUserId(user.getGuid());
        List<SimpleGrantedAuthority> collect = roles.stream().
                map(Role::getGuid).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        String tokenId = Long.toString(snowFlake.nextId());
        try {
            tokenId = Base64Utils.encryptBase64(tokenId.getBytes());
        } catch (Exception e) {
        }
        return Mono.just(new JwtTokenUser(user.getCode(), user.getPassword(), Integer.valueOf(user.getStatus()),
                collect, tokenId));
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
        snowFlake = SnowFlakeFactory.getSnowFlake();
    }
}
