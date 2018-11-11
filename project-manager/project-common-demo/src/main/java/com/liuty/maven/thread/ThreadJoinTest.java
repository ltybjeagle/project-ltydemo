package com.liuty.maven.thread;

/**
 * Created by liutianyang on 2018/11/10.
 */
public class ThreadJoinTest {

    public static void main(String ...args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
        System.out.println("main start ...");
        try {
            System.out.println("main wating ...");
            thread.join(); // 阻塞等待thread结束后，主线程在执行
            System.out.println("main end ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("MyRunnable start ...");
                Thread.sleep(1000);
                System.out.println("MyRunnable end ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
