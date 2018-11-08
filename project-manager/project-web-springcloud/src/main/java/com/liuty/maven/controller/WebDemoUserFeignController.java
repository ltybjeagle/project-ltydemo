package com.liuty.maven.controller;

import com.liuty.maven.entity.DemoUser;
import com.liuty.maven.feignclient.DemoUserFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;

//@Import(FeignClientsConfiguration.class)
@RestController
public class WebDemoUserFeignController {

    @Autowired
    private DemoUserFeignClient demoUserFeignClient;

//    @HystrixCommand(fallbackMethod = "findByIdUserFallBack", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
//            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1000")
//    }, threadPoolProperties = {
//            @HystrixProperty(name = "coreSize", value = "1"),
//            @HystrixProperty(name = "maxQueueSize", value = "10")
//    })
    @GetMapping("/feignUser/{id}")
    public DemoUser findByIdUser(@PathVariable(value = "id") String id) {
        return this.demoUserFeignClient.findById(id);
    }

    /**
     * Hystrix断路器开启，提供默认方法
     * @param id
     * @return
     */
    /*
    public DemoUser findByIdUserFallBack(String id) {
        DemoUser demoUser = new DemoUser();
        demoUser.setGuid("-1");
        demoUser.setName("默认用户");
        demoUser.setCode("000000");
        return demoUser;
    }*/

    /*
    private DemoUserFeignClient userUserFeignClient;
    private DemoUserFeignClient adminUserFeignClient;

    @Autowired
    public WebDemoUserFeignController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        this.userUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("user", "password1"))
                .target(DemoUserFeignClient.class, "http://microservice-provider-user/");
        this.adminUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("admin", "password2"))
                .target(DemoUserFeignClient.class, "http://microservice-provider-user/");
    }

    @GetMapping("/feignUser-user/{id}")
    public DemoUser findByIdUser(@PathVariable String id) {
        return this.userUserFeignClient.findById(id);
    }

    @GetMapping("/feignUser-admin/{id}")
    public DemoUser findByIdAdmin(@PathVariable String id) {
        return this.adminUserFeignClient.findById(id);
    }
    */
}
