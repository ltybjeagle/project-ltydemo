package com.liuty.maven.springutil.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class WebApiAutoConfiguration {


    @Value("${springfox.api.group:[your api group name]}")
    private String apiGroupName;

    @Value("${springfox.api.title:[set a api title via 'springfox.api.title']}")
    private String title;

    @Value("${springfox.api.description:[add your api description via 'springfox.api.description']}")
    private String desc;
    @Value("${springfox.api.version:[set specific api version via 'springfox.api.version']}")
    private String version;

    @Value("${springfox.api.termsOfServiceUrl:[set termsOfServiceUrl via 'springfox.api.termsOfServiceUrl']}")
    private String termsOfServiceUrl;

    @Value("${springfox.api.contact:[set contact via 'springfox.api.contact']}")
    private String contact;

    @Value("${springfox.api.license:Your WebAPI License}")
    private String license;

    @Value("${springfox.api.licenseUrl:http://maven.liuty.com}")
    private String licenseUrl;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(apiGroupName)
                .apiInfo(new ApiInfo(title, desc, version, termsOfServiceUrl
                        , new Contact(contact, "", ""), license, licenseUrl, new ArrayList()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liuty.maven.controller"))
                .paths(PathSelectors.any()).build();
    }
}
