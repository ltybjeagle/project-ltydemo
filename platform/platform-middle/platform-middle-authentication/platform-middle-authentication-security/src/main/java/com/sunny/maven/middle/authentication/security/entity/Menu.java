package com.sunny.maven.middle.authentication.security.entity;

import lombok.Data;

import java.util.List;

/**
 * @author SUNNY
 * @description: 资源实体类
 * @create: 2023/8/14 10:32
 */
@Data
public class Menu {
    private Integer id;
    private String pattern;
    private List<Role> roles;
}
