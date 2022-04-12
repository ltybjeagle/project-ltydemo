package com.sunny.maven.security.provider;

import com.sunny.maven.security.exception.Oauth2VerificationCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 认证校验逻辑
 * @create: 2022-02-09 15:00
 */
@Component
public class Oauth2AuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        // 获取详细信息
        Oauth2WebAuthenticationDetails details = (Oauth2WebAuthenticationDetails) authentication.getDetails();
        // 验证码校验状态失败，抛出异常
        if (!details.isImageCodeIsRight()) {
            throw new Oauth2VerificationCodeException();
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    /**
     * 构造注入
     * @param oauth2UserDetailService 自定义用户逻辑
     * @param passwordEncoder 密码编码处理
     */
    public Oauth2AuthenticationProvider(UserDetailsService oauth2UserDetailService,
                                        PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(oauth2UserDetailService);
        this.setPasswordEncoder(passwordEncoder);
    }
}
