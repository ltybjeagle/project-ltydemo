package com.sunny.maven.core.utils.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author SUNNY
 * @description: ReentrantReadWriteLock测试
 * @create: 2022-08-18 14:38
 */
@Slf4j
public class ReentrantReadWriteLockTest {
    @Test
    public void reentrantReadWriteLockTest() throws InterruptedException {
        ReentrantReadWriteLock rrwLock = new ReentrantReadWriteLock();
        ReadThread rt1 = new ReadThread("rt1", rrwLock);
        ReadThread rt2 = new ReadThread("rt2", rrwLock);
        WriteThread wt1 = new WriteThread("wt1", rrwLock);

        rt1.start();
        rt2.start();
        wt1.start();

        Thread.sleep(10000);
    }

    class ReadThread extends Thread {
        private ReentrantReadWriteLock rrwLock;
        public ReadThread(String name, ReentrantReadWriteLock rrwLock) {
            super(name);
            this.rrwLock = rrwLock;
        }
        @Override
        public void run() {
            log.info("{} trying to lock", Thread.currentThread().getName());
            try {
                rrwLock.readLock().lock();
                log.info("{} lock successfully", Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rrwLock.readLock().unlock();
                log.info("{} unlock successfully", Thread.currentThread().getName());
            }
        }
    }

    class WriteThread extends Thread {
        private ReentrantReadWriteLock rrwLock;
        public WriteThread(String name, ReentrantReadWriteLock rrwLock) {
            super(name);
            this.rrwLock = rrwLock;
        }
        @Override
        public void run() {
            log.info("{} trying to lock", Thread.currentThread().getName());
            try {
                rrwLock.writeLock().lock();
                log.info("{} lock successfully", Thread.currentThread().getName());
            } finally {
                rrwLock.writeLock().unlock();
                log.info("{} unlock successfully", Thread.currentThread().getName());
            }
        }
    }
}
