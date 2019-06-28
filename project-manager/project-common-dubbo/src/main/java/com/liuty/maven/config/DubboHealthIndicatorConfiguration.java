package com.liuty.maven.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述: Dubbo接口服务检测配置类
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/28
 */
@EnableAutoConfiguration
@Configuration
@ConditionalOnClass(name = {"com.alibaba.dubbo.rpc.Exporter"})
public class DubboHealthIndicatorConfiguration {

    @Autowired
    HealthAggregator healthAggregator;

    @Autowired(required = false)
    Map<String, ReferenceBean> references;

    @Bean
    public HealthIndicator dubboHealthIndicator() {
        Map<String, HealthIndicator> indicators = new HashMap<>();
        for (String key : references.keySet()) {
            final ReferenceBean bean = references.get(key);
            indicators.put(key.startsWith("&") ? key.replaceFirst("&", "") : key
                    , new DubboHealthIndicator(bean));
        }
        return new CompositeHealthIndicator(healthAggregator, indicators);
    }
}
