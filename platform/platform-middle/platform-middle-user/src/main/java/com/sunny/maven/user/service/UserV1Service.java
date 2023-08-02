package com.sunny.maven.user.service;

import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/7/24 11:30
 */
@Service
public class UserV1Service {
    public String query() {
        return "version1";
    }
}
