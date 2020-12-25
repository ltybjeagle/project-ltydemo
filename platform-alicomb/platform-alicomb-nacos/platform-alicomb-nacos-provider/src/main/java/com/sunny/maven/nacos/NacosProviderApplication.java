package com.sunny.maven.nacos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 17:35
 * @description：服务提供者
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication {
    public static void main(String ...args) throws Exception {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @RestController
    @RefreshScope
    class EchoController {

        @Value("${useLocalCache:false}")
        private boolean useLocalCache;

        @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
        public String echo(@PathVariable String string) {
            System.out.println("useLocalCache : " + useLocalCache);
            return "Hello Nacos Discovery " + string;
        }
    }
}
