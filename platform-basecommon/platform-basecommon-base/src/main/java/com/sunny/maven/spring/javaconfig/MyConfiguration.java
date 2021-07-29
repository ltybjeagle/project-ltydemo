package com.sunny.maven.spring.javaconfig;

import com.sunny.maven.spring.annotation.PersonAnnService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author SUNNY
 * @description: Config配置类
 * @create: 2021-07-28 22:17
 */
@Import({CustomImportSelector.class})
@Configuration
public class MyConfiguration {

    /**
     * 定义BEAN
     * ConditionalOnClass控制条件
     * @return BEAN对象
     */
    @ConditionalOnClass(PersonAnnService.class)
    @Bean
    public PersonConfigService personConfigService() {
        return new PersonConfigService();
    }
}
