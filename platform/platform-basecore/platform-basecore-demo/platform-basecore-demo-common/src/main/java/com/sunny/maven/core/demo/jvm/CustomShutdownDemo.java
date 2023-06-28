package com.sunny.maven.core.demo.jvm;

import sun.misc.Signal;

/**
 * @author SUNNY
 * @description: ShutdownDemo
 * @create: 2023/6/27 17:15
 */
public class CustomShutdownDemo {
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                System.out.println("钩子函数被执行，可以在这里关闭资源。")
        ));
    }

    public static void main(String[] args) {
        // custom signal kill
        Signal sg = new Signal("INT");
        Signal.handle(sg, sig -> {
            System.out.println("接收到信号量：" + sig.getName());
            // 监听信号量，通过System.exit(0)正常关闭JVM，触发关闭钩子执行收尾工作
            System.exit(0);
        });
        // other logic
        new CustomShutdownDemo().start();
        System.out.println("主应用程序在执行，正常关闭。");
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
