package com.liuty.maven;

import com.liuty.maven.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintStream;

/**
 * @Description: 微服务应用，spring cloud服务提供方
 * JVM监控：
 * java instrumentation
 * java agent
 *
 * JVMTI
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class MainApplication {
    public static void main(String[] args) {
        /**
         * 直接启动SpringApplication.run(MainApplication.class, args);
         * 流程：
         * 1、SpringApplication初始化
         *      classpath路径下，判断是否存在ConfigurableWebApplicationContext特征类，确定是否创建WEB上下文
         *      SpringFactoriesLoader加载classpath路径下，ApplicationContextInitializer
         *      SpringFactoriesLoader加载classpath路径下，ApplicationListener
         * 2、SpringFactoriesLoader查找并加载SpringApplicationRunListeners,调用started()方法
         * 3、创建并配置Environment
         * 4、调用SpringApplicationRunListeners.environmentPrepared()方法
         * 5、判断SpringApplication的showBanner属性是否为true，打印Banner
         * 6、创建ApplicationContext，并将Environment设置给ApplicationContext
         * 7、使用SpringFactoriesLoader查找并加载classpath路径下的ApplicationContextInitializer，调用initialize()方法
         * 8、调用SpringApplicationRunListeners.contextPrepared()方法
         * 9、将@EnableAutoConfiguration获取的配置加载到ApplicationContext
         * 10、调用SpringApplicationRunListeners.contextLoaded()方法
         * 11、调用ApplicationContext.refresh()方法
         * 12、查找ApplicationContext，看是否配置了CommandLineRunner，配置了的话执行他们
         * 13、调用SpringApplicationRunListeners.finished()方法
         */
        SpringApplication bootstrap = new SpringApplication(MainApplication.class);
        bootstrap.setBanner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                // 输出自定义系统标识

            }
        });
        bootstrap.setBannerMode(Banner.Mode.CONSOLE);
        bootstrap.run(args);
    }
}
