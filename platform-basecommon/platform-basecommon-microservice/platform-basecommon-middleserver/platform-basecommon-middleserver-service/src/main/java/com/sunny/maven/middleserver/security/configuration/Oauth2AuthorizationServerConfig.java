package com.sunny.maven.middleserver.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * @author SUNNY
 * @description: 认证服务器
 * @create: 2022-04-13 17:06
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 加密方式
     */
    private PasswordEncoder passwordEncoder;
    /**
     * 认证管理器
     */
    private AuthenticationManager authenticationManager;

    /**
     * 配置被允许访问此认证服务器的客户端详细信息
     * 1.内存管理
     * 2.数据库管理方式
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().
                // 客户端名称
                withClient("test-pc").
                // 客户端密码
                secret(passwordEncoder.encode("123456")).
                // 资源id,商品资源
                resourceIds("oauth2-server").
                // 授权类型, 可同时支持多种授权类型
                authorizedGrantTypes("authorization_code", "password", "implicit",
                        "client_credentials", "refresh_token").
                // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                scopes("all").
                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码
                autoApprove(false).
                // 客户端回调地址
                redirectUris("http://www.baidu.com/");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 密码模式需要配置认证管理器
        endpoints.authenticationManager(authenticationManager);
    }

    /**
     * 构造函数
     * @param passwordEncoder 加密方式
     * @param authenticationManager 认证管理器
     */
    @Autowired
    public Oauth2AuthorizationServerConfig(PasswordEncoder passwordEncoder,
                                           AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
}
