package com.liuty.maven.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/17
 */
@Component
@ConfigurationProperties(prefix = "mytest")
@Data
public class MyProperties {
    private String name;
    private String value;
}
