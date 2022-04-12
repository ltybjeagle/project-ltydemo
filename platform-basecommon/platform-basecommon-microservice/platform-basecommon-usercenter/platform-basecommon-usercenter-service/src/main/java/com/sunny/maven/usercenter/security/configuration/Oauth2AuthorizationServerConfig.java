package com.sunny.maven.usercenter.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * @author SUNNY
 * @description: Oauth2 配置授权中心信息（EnableAuthorizationServer：开启认证授权中心）
 * @create: 2022-02-24 11:01
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 用户服务
     */
    private UserDetailsService oauth2UserDetailService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
//    @Autowired
//    private TokenEnhancer jwtTokeEnhancer;
    /**
     * Token信息配置
     */
    private TokenProperties tokenProperties;

    /**
     * 配置客户端详情服务
     * 可以把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用in-memory存储，将客户端的信息存储在内存中
        clients.inMemory()
                // 创建了一个客户端(对应一个服务节点)
                .withClient(tokenProperties.getClientId())
                // 客户端secret值，使用BCrypt加密，格式：{加密类型}加密结果
                .secret("{bcrypt}" + tokenProperties.getClientSecret())
                // 授权方式: 1.authorization code，2.implicit，3.password ，4.client credentials
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                // 客户端的域
                .scopes("all")
                // access_token 过期时间，1小时过期
                .accessTokenValiditySeconds(tokenProperties.getAccessTokenValidateSeconds())
                // refresh_token 过期时间，
                .refreshTokenValiditySeconds(tokenProperties.getAccessTokenValidateSeconds());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 开启/oauth/check_token验证端口认证权限访问
        security.checkTokenAccess("isAuthenticated()");
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 指定认证管理器
        endpoints.authenticationManager(authenticationManagerBean());
        // 指定token存储位置
        endpoints.tokenStore(tokenStore);
        // 自定义token生成方式
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        //tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokeEnhancer, jwtAccessTokenConverter));
        endpoints.tokenEnhancer(tokenEnhancerChain);
        endpoints.userDetailsService(oauth2UserDetailService);
        // 配置TokenServices参数
        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints
                .getDefaultAuthorizationServerTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        // 复用refresh token
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        endpoints.tokenServices(tokenServices);
        super.configure(endpoints);
    }

    /**
     * 不定义没有password grant_type，密码模式需要AuthenticationManager支持
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() {
        AuthenticationManager authenticationManager =
                authentication -> daoAuthenticationProvider().authenticate(authentication);
        return authenticationManager;
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(oauth2UserDetailService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * password加密方式
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 加密方式
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //converter.setSigningKey(jwtTokenProperties.getJwtSigningKey());
        return converter;
    }

    /**
     * 构造函数
     * @param oauth2UserDetailService 用户服务
     * @param tokenProperties Token信息配置
     */
    @Autowired
    public Oauth2AuthorizationServerConfig(UserDetailsService oauth2UserDetailService,
                                           TokenProperties tokenProperties) {
        this.oauth2UserDetailService = oauth2UserDetailService;
        this.tokenProperties = tokenProperties;
    }
}
