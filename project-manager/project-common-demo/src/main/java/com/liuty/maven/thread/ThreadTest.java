package com.liuty.maven.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by liutianyang on 2018/11/10.
 * 线程实现方式
 */
public class ThreadTest {

    public static void main(String ...args) {
        MyThread myThread = new MyThread();
        myThread.start();

        RunableTask task = new RunableTask();
        new Thread(task).start();

        FutureTask<String> futureTask = new FutureTask<>(new CallerTask());
        new Thread(futureTask).start();
        try {
            String result = futureTask.get(); // 阻塞获取结果
            System.out.println(result);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 继承Thread ， 无返回值
     */
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread extends Thread");
        }
    }

    /**
     * 实现Runnable ， 无返回值
     */
    public static class RunableTask implements Runnable {
        @Override
        public void run() {
            System.out.println("RunableTask implements Runnable");
        }
    }

    /**
     * 实现Callable<T> ,有返回值
     */
    public static class CallerTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "CallerTask implements Callable";
        }
    }
}
