package com.sunny.maven.core.utils.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SUNNY
 * @description: ReentrantLock测试
 * @create: 2022-08-17 11:30
 */
@Slf4j
public class ReentrantLockTest {

    @Test
    public void reentrantLockTest() throws InterruptedException {
        Lock lock = new ReentrantLock(true);
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        MyThread t3 = new MyThread("t3", lock);

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(10000);
    }

    class MyThread extends Thread {
        private Lock lock;
        public MyThread(String name, Lock lock) {
            super(name);
            this.lock = lock;
        }
        @Override
        public void run() {
            lock.lock();
            try {
                log.info("{} running", Thread.currentThread());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
