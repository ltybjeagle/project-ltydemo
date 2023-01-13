package com.sunny.maven.core.utils.locksupporttest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author SUNNY
 * @description: LockSupport测试
 * @create: 2022-08-15 17:39
 */
@Slf4j
public class LockSupportTest {

    @Test
    public void parkAndUnParkTest() {
        MyThread myThread = new MyThread(Thread.currentThread());
        myThread.start();
        log.info("before park");
        // 获取许可，LockSupport.park()不需要锁挂起当前线程，如果获取锁，挂起后也不释放锁
        LockSupport.park("ParkAndUnparkTest");
        log.info("after park");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MyThread extends Thread {
        private Object object;
        public MyThread(Object object) {
            this.object = object;
        }
        @Override
        public void run() {
            log.info("before unpark");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 获取blocker
            log.info("Blocker info {}", LockSupport.getBlocker((Thread) object));
            // 释放许可
            LockSupport.unpark((Thread) object);
            // 休眠500ms，保证先执行park中的setBlocker(t, null);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 再次获取blocker
            log.info("Blocker info {}", LockSupport.getBlocker((Thread) object));
            log.info("after unpark");
        }
    }
}
