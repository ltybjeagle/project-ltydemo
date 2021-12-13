package com.sunny.maven.template;

import com.sunny.maven.configuration.MyCacheProperties;

/**
 * @author SUNNY
 * @description: Cache操作类
 * @create: 2021-11-24 10:45
 */
public class MyCacheTemplate {

    /**
     * CacheProperties 缓存配置对象
     */
    private MyCacheProperties myCacheProperties;

    /**
     * 无参构造器
     */
    public MyCacheTemplate() {}

    /**
     * 构造器
     * @param myCacheProperties 缓存配置对象
     */
    public MyCacheTemplate(MyCacheProperties myCacheProperties) {
        this.myCacheProperties = myCacheProperties;
    }

    /**
     * 获取Redis名称
     * @return String
     */
    public String getRedisName() {
        return myCacheProperties.getRedisName();
    }

}
