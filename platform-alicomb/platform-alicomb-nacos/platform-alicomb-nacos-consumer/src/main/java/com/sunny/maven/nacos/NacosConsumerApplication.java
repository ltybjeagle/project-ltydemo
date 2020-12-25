package com.sunny.maven.nacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 19:44
 * @description：Consumer消费端
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class NacosConsumerApplication {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @LoadBalanced
    @Bean
    public AsyncRestTemplate asyncRestTemplate(){
        return new AsyncRestTemplate();
    }

    public static void main(String ...args) throws Exception {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @FeignClient(name = "service-provider")
    public interface EchoService {
        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        String echo(@PathVariable("str") String str) ;
    }

    @RestController
    public class TestController {

        private final RestTemplate restTemplate;
        private final AsyncRestTemplate asyncRestTemplate;
        private final EchoService echoService;

        @Autowired
        public TestController(RestTemplate restTemplate, AsyncRestTemplate asyncRestTemplate,
                              EchoService echoService) {
            this.restTemplate = restTemplate;
            this.asyncRestTemplate = asyncRestTemplate;
            this.echoService = echoService;
        }

        @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
        public String rest(@PathVariable String str) {
            return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
        }

        @RequestMapping(value = "/echo-asyncRest/{str}", method = RequestMethod.GET)
        public String asyncRest(@PathVariable String str) throws Exception {
            ListenableFuture<ResponseEntity<String>> future =
                    asyncRestTemplate.getForEntity("http://service-provider/echo/" + str, String.class);
            return future.get().getBody();
        }

        @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
        public String feign(@PathVariable String str) throws Exception {
            return echoService.echo(str);
        }
    }
}
