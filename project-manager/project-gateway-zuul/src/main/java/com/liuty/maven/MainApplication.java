package com.liuty.maven;

import com.liuty.maven.filter.AccessFilter;
import com.liuty.maven.filter.MyFilterProcessor;
import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class MainApplication {

    /**
     * 网关请求过滤器
     * @return
     */
    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    /**
     * 服务路径映射，匹配不上按默认映射
     * 按版本映射，如：serviceName-v1对应到v1/serviceName
     * @return
     */
    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)"
                , "${version}/${name}");
    }

    public static void main(String ...args) {
        SpringApplication.run(MainApplication.class);
        FilterProcessor.setProcessor(new MyFilterProcessor());
    }
}
