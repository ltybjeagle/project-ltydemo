package com.sunny.maven.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SUNNY
 * @description: Cache配置类
 * @create: 2021-11-24 10:14
 */
@ConfigurationProperties("spring.mycache")
public class MyCacheProperties {
    /**
     * redis用户名
     */
    private String redisName;

    /**
     * 获取redis用户名
     * @return
     */
    public String getRedisName() {
        return redisName;
    }

    /**
     * 设置redis用户名
     * @param redisName redis用户名
     */
    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }
}
