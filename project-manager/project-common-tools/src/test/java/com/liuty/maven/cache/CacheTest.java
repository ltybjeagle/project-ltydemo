package com.liuty.maven.cache;

import com.liuty.maven.cache.mycache.MyCacheService;
import com.liuty.maven.entity.UserEntity;
import org.checkerframework.checker.units.qual.K;
import org.junit.Test;

import javax.cache.Cache;

/**
 * @描述:
 * @创建人: Sunny
 * @创建时间: 2019/4/20
 */
public class CacheTest {

    @Test
    public void myCacheTest() {
        ICacheService<String, UserEntity> cacheService = new MyCacheService<>(String.class, UserEntity.class);
        cacheService.put("test", new UserEntity());
        System.out.println(cacheService.get("test"));
    }
}
