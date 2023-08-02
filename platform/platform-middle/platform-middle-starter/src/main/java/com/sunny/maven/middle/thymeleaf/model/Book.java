package com.sunny.maven.middle.thymeleaf.model;

import lombok.Data;

/**
 * @author SUNNY
 * @description: Book实体类
 * @create: 2023/7/20 17:58
 */
@Data
public class Book {
    private Integer id;
    private String name;
    private String author;
}
