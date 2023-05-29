package com.sunny.maven.springframework.context;

import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.factory.Aware;

/**
 * @author SUNNY
 * @description: 实现此接口，既能感知到所属的 ApplicationContext
 * @create: 2023/5/28 12:59
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
