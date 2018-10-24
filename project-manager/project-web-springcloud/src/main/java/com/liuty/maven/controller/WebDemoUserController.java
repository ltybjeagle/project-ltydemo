package com.liuty.maven.controller;

import com.liuty.maven.entity.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebDemoUserController {

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("/user/{id}")
    public DemoUser findById(@PathVariable String id) {
        return this.restTemplate.getForObject("http://localhost:8000/" + id, DemoUser.class);
    }
}
