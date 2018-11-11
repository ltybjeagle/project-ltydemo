package com.liuty.maven.thread;

/**
 * Created by liutianyang on 2018/11/10.
 */
public class ThreadInterruptTest {

    public static void main(String ...args) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 判断线程中断状态，不撤销状态
                while (!Thread.currentThread().isInterrupted()) {

                }
                System.out.println(Thread.currentThread() + " hello");
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("main thread interrupt thread");
        thread.interrupt();
        thread.join();
        System.out.println("main is over");

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("threadOne begin sleep for 2000 seconds");
                    Thread.sleep(2000000); // 中断状态，抛出异常
                    System.out.println("threadOne awaking");
                } catch (InterruptedException ex) {
                    System.out.println("threadOne is interrupted while sleeping");
                    return ;
                }
            }
        });
        threadOne.start();
        Thread.sleep(1000);
        threadOne.interrupt();
        threadOne.join();
        System.out.println("main thread is over");
    }
}
