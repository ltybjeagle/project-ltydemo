package com.sunny.maven.core.utils.aqs;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SUNNY
 * @description: AbstractQueuedSynchonizer测试
 * @create: 2022-08-16 14:42
 */
@Slf4j
public class AbstractQueuedSynchonizerTest {

    @Test
    public void lockAndUnlockTest() throws InterruptedException {
        Lock lock = new ReentrantLock();
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);

        t1.start();
        t2.start();
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
                log.info("{}  running", Thread.currentThread());
            } finally {
                lock.unlock();
            }
        }
    }
}
