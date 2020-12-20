package com.sunny.maven.observabletest;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/13 20:47
 * @description：日志输出
 * @modified By：
 * @version: 1.0.0
 */
public interface ObservableLog {
    default void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}
