package com.liuty.maven.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/spring/dubbo-consumer.xml")
public class PropertiesConfiguration {

}
