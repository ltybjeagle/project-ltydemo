package com.sunny.maven;

import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/5 11:18
 * @description：ORM应用启动类
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
public class OrmSpringApplication implements ExitCodeGenerator {

    /**
     * 添加监听,监听类型：
     * app.addListeners(new MyEnvListener());
     * 1、在运行开始，但除了监听器注册和初始化以外的任何处理之前，会发送一个ApplicationStartedEvent。
     * 2、在Environment将被用于已知的上下文，但在上下文被创建前，会发送一个ApplicationEnvironmentPreparedEvent。
     * 3、在refresh开始前，但在bean定义已被加载后，会发送一个ApplicationPreparedEvent。
     * 4、在refresh之后，相关的回调处理完，会发送一个ApplicationReadyEvent，表示应用准备好接收请求了。
     * 5、启动过程中如果出现异常，会发送一个ApplicationFailedEvent。
     *
     * // 流式构建
     * new SpringApplicationBuilder()
     *         .bannerMode(Banner.Mode.OFF)
     *         .run(args);
     */
    public static void main(String ...args) throws Exception {
        SpringApplication app = new SpringApplication(OrmSpringApplication.class);
        // 关闭banner
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    /**
     * 返回状态码
     * @return
     */
    @Override
    public int getExitCode() {
        return 100;
    }
}
