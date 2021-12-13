package com.sunny.maven.configuration;

import com.sunny.maven.template.MyCacheTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: MyCache自动配置类
 * @create: 2021-11-24 10:51
 */
@Configuration
@EnableConfigurationProperties(MyCacheProperties.class)
public class MyCacheAutoConfigure {

    /**
     * 初始化缓存操作类
     * @param myCacheProperties 缓存配置
     * @return MyCacheTemplate
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.mycache", value = "enabled", havingValue = "true")
    public MyCacheTemplate userClient(MyCacheProperties myCacheProperties) {
        return new MyCacheTemplate(myCacheProperties);
    }
}
