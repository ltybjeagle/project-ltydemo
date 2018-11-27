package com.liuty.maven.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalController {

    @RequestMapping("/local/zuullocal")
    public String localForward() {
        return "local forward";
    }
}
