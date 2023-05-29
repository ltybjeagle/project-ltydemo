package com.sunny.maven.core.demo.concurrent.executor.state;

/**
 * @author SUNNY
 * @description: 线程的各种状态，测试线程的生命周期
 * @create: 2023/5/24 18:36
 */
public class ThreadState {

    public static void main(String[] args) {
        new Thread(new WaitingTime(), "WaitingTimeThread").start();
        new Thread(new WaitingState(), "WaitingStateThread").start();

        // BlockedThread-01线程会抢到锁，BlockedThread-02线程会阻塞
        new Thread(new BlockedThread(), "BlockedThread-01").start();
        new Thread(new BlockedThread(), "BlockedThread-02").start();
    }
}
