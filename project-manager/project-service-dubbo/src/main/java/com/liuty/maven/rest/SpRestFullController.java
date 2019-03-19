package com.liuty.maven.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpRestFullController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello spring";
    }
}
