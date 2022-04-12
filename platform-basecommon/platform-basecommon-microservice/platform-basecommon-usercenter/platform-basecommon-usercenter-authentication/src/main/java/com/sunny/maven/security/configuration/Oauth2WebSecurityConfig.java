package com.sunny.maven.security.configuration;

import com.sunny.maven.security.exception.Oauth2AuthenticationFailureHandler;
import com.sunny.maven.security.filter.Oauth2VerificationCodeFilter;
import com.sunny.maven.security.strategy.Oauth2InvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author SUNNY
 * @description: 安全认证配置类
 * @create: 2022-01-27 23:05
 */
@EnableWebSecurity(debug = true)
public class Oauth2WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义认证补充信息
     */
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> oauth2AuthenticationDetailsSource;
    /**
     * 认证校验逻辑
     */
    private AuthenticationProvider oauth2AuthenticationProvider;
    /**
     * 会话注册表
     */
    private SpringSessionBackedSessionRegistry redisSessionRegistry;

    /**
     * 不定义没有password grant_type，密码模式需要AuthenticationManager支持
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 设置自定义认证校验逻辑
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(oauth2AuthenticationProvider);
    }

    /**
     * 安全特性
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * csrf().disable()
         * 开启授权认证
         * 开启跨域共享，关闭同源策略【允许跨域】
         */
        http.cors();
        // CsrfToken校验值 存储在CooKie
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        // 配置路径拦截规则
        http.authorizeRequests().
                antMatchers("/admin/api/**").hasRole("ADMIN").
                antMatchers("/user/api/**").hasRole("ADMIN").
                antMatchers("/app/api/**", "/authentication/authProLogin").permitAll().
                anyRequest().authenticated();

        // 登录配置
        http.formLogin().loginProcessingUrl("/authentication/authLogin").
                authenticationDetailsSource(oauth2AuthenticationDetailsSource).
                failureHandler(new Oauth2AuthenticationFailureHandler());

         // 添加过滤器，在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(new Oauth2VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
        /*
         * Session的并发控制,这里设为最多一个，只允许一个用户登录,如果同一个账户两次登录,
         * 那么第一个账户将被踢下线
         * 其缺省实现是将session记录在内存map中，因此不能用于集群环境中，
         * 服务器1中记录的信息和服务器2记录的信息并不相同
         */
        http.sessionManagement().maximumSessions(1).
                // 使用 session 提供的会话注册表
                sessionRegistry(redisSessionRegistry).
                // 阻止新会话登录，默认是false
                maxSessionsPreventsLogin(true).
                and().
                // 配置session失效策略
                invalidSessionStrategy(new Oauth2InvalidSessionStrategy());
    }

    /**
     * 跨域请求设置
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许从百度站点跨域
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://www.baidu.com"));
        // 允许使用GET，POST方法
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));
        // 允许携带凭证
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有URL生效
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * 构造注入
     * @param oauth2AuthenticationDetailsSource 自定义认证补充信息
     * @param oauth2AuthenticationProvider 认证校验逻辑
     */
    @Autowired
    public Oauth2WebSecurityConfig(
            AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> oauth2AuthenticationDetailsSource,
            AuthenticationProvider oauth2AuthenticationProvider,
            SpringSessionBackedSessionRegistry redisSessionRegistry) {
        this.oauth2AuthenticationDetailsSource = oauth2AuthenticationDetailsSource;
        this.oauth2AuthenticationProvider = oauth2AuthenticationProvider;
        this.redisSessionRegistry = redisSessionRegistry;
    }
}
