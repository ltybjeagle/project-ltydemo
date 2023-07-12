package com.sunny.maven.microservice.gateway.predicate;

import lombok.Data;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 接收配置文件中的参数
 * @create: 2023/7/12 15:08
 */
@Data
public class NameRoutePredicateConfig implements Serializable {
    private static final long serialVersionUID = -3289515863427972825L;
    private String name;
}
