package com.liuty.maven.thread;

/**
 * Created by liutianyang on 2018/11/10.
 */
public class ThreadYieldTest {

    public static void main(String ...args) {
        new YieldTest();
        new YieldTest();
        new YieldTest();
    }

    public static class YieldTest implements Runnable {
        public YieldTest() {
            Thread thread = new Thread(this);
            thread.start();
        }
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                if (i % 5 == 0) {
                    System.out.println(Thread.currentThread() + "Yield cpu ...");
                    Thread.yield(); // 交出CPU时间片段，由线程调度器重新调度线程分配时间片（线程不挂起）
                }
            }
            System.out.println(Thread.currentThread() + "is Over");
        }
    }
}
