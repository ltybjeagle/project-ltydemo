package com.sunny.maven.security.configuration;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author SUNNY
 * @description: 自定义功能配置
 * @create: 2022-02-08 17:25
 */
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.security.service", "com.sunny.maven.security.controller",
        "com.sunny.maven.security.provider"})
@MapperScan(basePackages = {"com.sunny.maven.security.mapper"})
public class Oauth2CommBeanConfig {

    /**
     * 定义验证码
     * @return Producer
     */
    @Bean
    public Producer captcha() {
        // 配置图形验证码的基本参数
        Properties properties = new Properties();
        // 图片宽度
        properties.setProperty("kaptcha.image.width", "150");
        // 图片高度
        properties.setProperty("kaptcha.image.height", "50");
        // 字符集
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        // 字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
