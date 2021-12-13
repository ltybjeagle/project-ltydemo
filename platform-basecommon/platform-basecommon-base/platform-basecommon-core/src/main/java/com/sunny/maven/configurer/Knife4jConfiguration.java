package com.sunny.maven.configurer;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author SUNNY
 * @description: Knife4j 配置类
 * @create: 2021-11-19 10:23
 */
public class Knife4jConfiguration {

    @Bean(value = "defaultDocket")
    public Docket defaultDocket() {
        // 联系人信息
        Contact contact = new Contact("微服务接口", "https://www.javastack.cn", "190290369@qq.com");

        // 创建 Docket
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Knife4j 测试")
                        .description("Knife4j Test")
                        .termsOfServiceUrl("https://www.javastack.cn")
                        .contact(contact)
                        .version("1.0")
                        .build())
                .groupName("1.x")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.javastack.springboot.knife4j.api"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
