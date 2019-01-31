package com.liuty.maven;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {

    public static void main(String ...args) {
        new SpringApplicationBuilder(ConfigApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
