package com.sunny.maven.spring.xmlbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SUNNY
 * @description: Spring Bean
 * @create: 2021-07-27 23:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonService {
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
}
