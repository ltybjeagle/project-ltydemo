package com.sunny.maven.thread;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/9/5 19:16
 * @description：自定义线程类
 * @modified By：
 * @version: 1.0.0
 */
public class MyThread implements Runnable {
    @Override
    public void run() {
        throw new NullPointerException();
    }

    public static void main(String ...args) throws Exception {
        Thread thread = new Thread(new MyThread());
        thread.setUncaughtExceptionHandler(new MyExceptionHandler());
        thread.start();
    }
}
