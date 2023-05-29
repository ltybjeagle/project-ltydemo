package com.sunny.maven.core.demo.concurrent.executor.state;

/**
 * @author SUNNY
 * @description: 线程在Warting上等待
 * @create: 2023/5/24 17:57
 */
public class WaitingState implements Runnable {
    @Override
    public void run() {
        while (true) {
            synchronized (WaitingState.class) {
                try {
                    WaitingState.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
