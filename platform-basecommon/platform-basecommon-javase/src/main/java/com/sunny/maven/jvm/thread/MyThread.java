package com.sunny.maven.jvm.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 21:15
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

        // 创建线程池
        Executor executor = Executors.newCachedThreadPool((Runnable runnable) -> {
            Thread thread_exe = Executors.defaultThreadFactory().newThread(runnable);
            thread_exe.setDaemon(true);
            return thread_exe;
        });
    }
}
