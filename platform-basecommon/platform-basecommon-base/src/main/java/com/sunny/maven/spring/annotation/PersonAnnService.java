package com.sunny.maven.spring.annotation;

import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 注解定义BEAN
 * @create: 2021-07-28 18:31
 */
@Service
public class PersonAnnService {

    /**
     * 获取数据
     * @return String
     */
    public String get() {
        return "data";
    }
}
