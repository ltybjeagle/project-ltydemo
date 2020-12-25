package com.sunny.maven.samples;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/9/7 19:59
 * @description：服务启动类
 * @modified By：
 * @version: 1.0.0
 */
@ComponentScan(basePackages = {"com.sunny.maven.samples"})
public class AppMain {
    public static void main(String[] args) {
        BeanUtils.init();
    }
}
