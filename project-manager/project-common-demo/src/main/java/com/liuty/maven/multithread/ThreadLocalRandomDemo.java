package com.liuty.maven.multithread;
/**/
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liutianyang on 2018/11/11.
 *
 * Random：随机数类，伪随机（存在规律）
 * 种子：一个long类型的数字
 * 计算方式：使用种子计算新种子，使用新种子计算随机数
 * 多线程问题：
 *      1、多线程获取到相同的种子，计算出相同的新种子，导致计算出相同的随机数
 *      2、Random使用原子变量消除了第一个问题，基于CAS逻辑，保证多线程里只能有一个线程能将新种子赋值旧种子，
 *      其他失败的线程重试计算新的种子在赋值，避免的多线程问题，但是引入竞争，性能存在问题
 * ThreadLocalRandom：并发随机数类，伪随机
 * 计算方式：同Random
 * 线程安全逻辑：
 *      1、ThreadLocalRandom实例只是计算的函数
 *      2、随机数的种子数值保存在线程私有的变量里，每个线程都有自己的随机数种子，同ThreadLocal
 */
public class ThreadLocalRandomDemo {

    public static void main(String ...args) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(threadLocalRandom.nextInt(5));
        }
    }
}
