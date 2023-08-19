package com.sunny.maven.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author SUNNY
 * @description: Swagger配置类
 * @create: 2023/8/17 12:18
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).
                apiInfo(new ApiInfoBuilder().
                        description("微服务平台接口文档").
                        contact(new Contact("Sunny", "", "")).
                        title("API接口").
                        version("V1.0").
                        license("Apache2.0").
                        licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").
                        build()).
                select().
                apis(RequestHandlerSelectors.basePackage("com.sunny.maven")).
                paths(PathSelectors.any()).
                build();
    }

}
