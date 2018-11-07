package com.liuty.maven.controller;

import com.liuty.maven.entity.DemoUser;
import com.liuty.maven.feignclient.DemoUserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebDemoUserFeignController {

    @Autowired
    public DemoUserFeignClient demoUserFeignClient;

    @GetMapping("/feignUser/{id}")
    public DemoUser findById(@PathVariable String id) {
        return this.demoUserFeignClient.findById(id);
    }
}
