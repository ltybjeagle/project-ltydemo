package com.sunny.maven.thread;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/9/5 19:17
 * @description：线程异常监听类
 * @modified By：
 * @version: 1.0.0
 */
public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("--------------exception---------------");
        System.out.println("线程信息：" + t.toString());
        System.out.println("异常信息：" + e.getMessage());
    }
}
