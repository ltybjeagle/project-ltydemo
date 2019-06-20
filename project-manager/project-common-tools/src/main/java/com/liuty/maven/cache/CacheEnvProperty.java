package com.liuty.maven.cache;

import com.liuty.maven.util.spring.SpringContextUtil;
import org.springframework.core.env.Environment;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/23
 */
public class CacheEnvProperty {

    private static Environment environment = (Environment) SpringContextUtil.getBean(Environment.class);

    /**
     * 获取配置值
     * @param proName
     * @return
     */
    public static String getProperties(String proName) {
        return environment.getProperty(proName);
    }
}
