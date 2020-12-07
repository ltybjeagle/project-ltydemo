package com.sunny.maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/7 15:06
 * @description：服务消费者
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String ...args) throws Exception {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @RestController
    public class TestController {
        private final RestTemplate restTemplate;

        @Autowired
        public TestController(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
        public String rest(@PathVariable String str) {
            return restTemplate.getForObject("http://platform-springcloud-provider/echo/" + str, String.class);
        }
    }
}
