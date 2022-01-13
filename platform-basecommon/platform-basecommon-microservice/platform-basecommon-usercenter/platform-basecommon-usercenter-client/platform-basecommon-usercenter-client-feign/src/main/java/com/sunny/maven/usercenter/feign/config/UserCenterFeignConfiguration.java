package com.sunny.maven.usercenter.feign.config;

import com.sunny.maven.common.feign.interceptor.FeignRequestInterceptor;
import feign.Logger;
import feign.Request;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * 通过配置文件的方式来指定 Feign 的配置，如下：
 * # 链接超时时间
 * feign.client.config.feignName.connectTimeout=5000
 * # 读取超时时间
 * feign.client.config.feignName.readTimeout=5000
 * # 日志等级
 * feign.client.config.feignName.loggerLevel=full
 * # 重试
 * feign.client.config.feignName.retryer=com.example.SimpleRetryer
 * # 拦截器
 * feign.client.config.feignName.requestInterceptors[0]=com.example.FooRequestInterceptor
 * feign.client.config.feignName.requestInterceptors[1]=com.example.BarRequestInterceptor
 * # 编码器
 * feign.client.config.feignName.encoder=com.example.SimpleEncoder
 * # 解码器
 * feign.client.config.feignName.decoder=com.example.SimpleDecoder
 * # 契约
 * feign.client.config.feignName.contract=com.example.SimpleContract
 * @description: 用户中心Feign客户端配置类
 * @create: 2022-01-12 13:30
 */
@Configuration
@EnableFeignClients(basePackages = "com.sunny.maven.usercenter.feign.client")
public class UserCenterFeignConfiguration {
    /**
     * 日志级别
     * @return 日志级别
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 超时时间配置
     * Options 的第一个参数是连接超时时间（ms），默认值是 10×1000；第二个是取超时时间（ms），默认值是 60×1000
     * @return Options
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5000, TimeUnit.MILLISECONDS,
                10000, TimeUnit.MILLISECONDS, true);
    }

    /**
     * Feign 请求拦截器
     * @return FeignRequestInterceptor
     */
    @Bean
    public FeignRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
