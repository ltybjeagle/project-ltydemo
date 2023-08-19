package com.sunny.maven.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/11 13:40
 */
@RestController
@RequestMapping("/db")
public class DbController {
    @GetMapping("/hello")
    public String dba() {
        return "hello dba!";
    }

}
