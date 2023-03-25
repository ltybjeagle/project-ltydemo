package com.sunny.maven.springframework.core.io;

/**
 * @author SUNNY
 * @description: 资源加载器
 * @create: 2023-03-25 12:39
 */
public interface ResourceLoader {

    /**
     * Pseudo URL prefix for loading from the class path: "classpath:"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
