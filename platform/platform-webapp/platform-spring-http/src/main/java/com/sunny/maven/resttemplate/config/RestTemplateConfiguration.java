package com.sunny.maven.resttemplate.config;

import com.sunny.maven.resttemplate.interceptor.RestTemplateLogRecordInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/28 1:02
 * @description：RestTemplate配置类
 * @modified By：
 * @version: 1.0.0
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory =
                new BufferingClientHttpRequestFactory(factory);
        // motor服务有的服务响应时间较长，暂定半个小时
        factory.setReadTimeout(1800000);
        factory.setConnectTimeout(1800000);
        RestTemplate restTemplate = new RestTemplate(bufferingClientHttpRequestFactory);
        restTemplate.getInterceptors().add(new RestTemplateLogRecordInterceptor());
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

}
