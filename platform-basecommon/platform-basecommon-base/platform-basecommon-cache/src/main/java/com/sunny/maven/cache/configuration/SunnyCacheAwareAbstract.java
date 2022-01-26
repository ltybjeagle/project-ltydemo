package com.sunny.maven.cache.configuration;

import com.sunny.maven.cache.template.SunnyCacheTemplate;

import java.util.Objects;

/**
 * @author SUNNY
 * @description: 抽象缓存对象初始化类
 * @create: 2022-01-19 17:09
 */
public abstract class SunnyCacheAwareAbstract {
    /**
     * 初始化缓存方式，业务自行实现
     */
    public abstract void init();

    /**
     * 获取缓存对象
     * @return
     */
    protected SunnyCacheTemplate getSunnyCacheTemplate() {
        return this.sunnyCacheTemplate;
    }
    /**
     * 设置缓存对象
     * @param sunnyCacheTemplate 缓存对象
     */
    public void setSunnyCacheTemplate(SunnyCacheTemplate sunnyCacheTemplate) {
        if (Objects.isNull(this.sunnyCacheTemplate)) {
            this.sunnyCacheTemplate = sunnyCacheTemplate;
        }
    }
    private SunnyCacheTemplate sunnyCacheTemplate;
}
