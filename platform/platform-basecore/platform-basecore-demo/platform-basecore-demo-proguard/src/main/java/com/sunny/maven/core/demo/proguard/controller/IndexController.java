package com.sunny.maven.core.demo.proguard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/5/26 18:59
 */
@RestController
public class IndexController {

    @GetMapping
    public String index() {
        return "这是主页";
    }
}
