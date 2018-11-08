package com.liuty.maven.controller;

import com.liuty.maven.entity.DemoUser;
import com.liuty.maven.feignclient.DemoUserFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;

//@Import(FeignClientsConfiguration.class)
@RestController
public class WebDemoUserFeignController {

    @Autowired
    private DemoUserFeignClient demoUserFeignClient;

    //@GetMapping("/feignUser/{id}")
    @RequestMapping(value = "/feignUser/{id}", method = RequestMethod.GET)
    public DemoUser findByIdUser(@PathVariable(value = "id") String id) {
        return this.demoUserFeignClient.findById(id);
    }

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
