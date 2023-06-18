package com.sunny.maven.middle.authentication.jwt.userdetails;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 用户查询服务
 * @create: 2023-04-02 16:54
 */
@Service("userDetailsService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = User.builder().username(username).password("rFHEpKKoQ9AvI51E+bXh3Q==").roles("ADMIN").build();
        if (user == null) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        }
        return new JwtUserDetails(user.getUsername(), user.getPassword(), user.isEnabled() ? 1 : 0,
                user.getAuthorities());
    }
}
