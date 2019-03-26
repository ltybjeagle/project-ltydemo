package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 类注解说明：
 *      1、注解@SpringBootApplication：标注此应用是SPRING BOOT应用
 *      2、注解@EnableDiscoveryClient：标注为eureka客户端，激活eureka注册中心DiscoveryClient对象，可以获取注册中心信息
 *
 * @Description: Spring Boot应用
 *      1、application.yml配置文件里的属性信息，可以通过命令行JAR包执行的时候动态指定，
 *      如：java -jar 应用.jar 属性名=属性值
 *      配置属性的加载优先级：命令行配置 > 系统配置文件
 *      2、引入spring-boot-starter-actuator依赖，添加监控组件
 *      配置类监控信息：
 *          /actuator/conditions：自动化配置报告
 *          /actuator/beans：监控容器启动了那些BEAN
 *          /actuator/configprops：属性信息报告
 *          /actuator/env：环境属性报告
 *          /actuator/mappings：SpringMVC映射报告
 *          /actuator/info：应用自定义信息报告
 *      度量指标类监控信息：
 *          /actuator/metrics：进程运行时监控报告（线程、内存、垃圾回收）
 *          /actuator/health：监控系统各类健康指标
 *          /actuator/threaddump：执行一个线程dump
 *
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
