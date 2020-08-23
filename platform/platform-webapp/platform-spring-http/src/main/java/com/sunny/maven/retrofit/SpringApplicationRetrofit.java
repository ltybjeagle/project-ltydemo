package com.sunny.maven.retrofit;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/21 23:59
 * @description：启动类
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
@RetrofitScan("com.sunny.maven.retrofit.clientapi")
public class SpringApplicationRetrofit {
    public static void main(String ...args) {
        SpringApplication.run(SpringApplicationRetrofit.class);
    }
}
