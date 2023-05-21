package com.sunny.maven.core.common.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author SUNNY
 * @description: 自定义简易线程池
 * @create: 2023-04-07 14:14
 */
public class ThreadPoolExecutor implements Executor {

    private BlockingQueue<Runnable> workQueue;
    private List<WorkThread> workThreads = new ArrayList<>();
    private static final int DEFAULT_QUEUE_SIZE = 5;

    public ThreadPoolExecutor(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            WorkThread workThread = new WorkThread();
            workThread.start();
            workThreads.add(workThread);
        }
    }

    public ThreadPoolExecutor(int poolSize) {
        this(poolSize, new LinkedBlockingQueue<Runnable>(DEFAULT_QUEUE_SIZE));
    }

    @Override
    public void execute(Runnable task) {
        try {
            workQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        if (workThreads.size() > 0) {
            for (int i = 0; i < workThreads.size(); i++) {
                WorkThread workThread = workThreads.get(i);
                workThread.interrupt();
            }
        }
    }

    /**
     * 工作线程
     */
    class WorkThread extends Thread {
        @Override
        public void run(){
            Thread currentThread = Thread.currentThread();
            while (true) {
                try {
                    // 判断当前线程是否被中断
                    if (currentThread.isInterrupted()) {
                        break;
                    }

                    Runnable workTask = workQueue.take();
                    workTask.run();
                } catch (InterruptedException e) {
                    currentThread.interrupt();
                }
            }
        }
    }
}
