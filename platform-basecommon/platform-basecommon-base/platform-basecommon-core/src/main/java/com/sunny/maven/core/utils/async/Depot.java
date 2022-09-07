package com.sunny.maven.core.utils.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SUNNY
 * @description: 仓库类
 * @create: 2022-08-16 15:27
 */
@Slf4j
public class Depot {
    private int size;
    private int capacity;
    private Lock lock;
    private Condition fullCondition;
    private Condition emptyCondition;

    /**
     * 构造函数
     * @param capacity
     */
    public Depot(int capacity) {
        this.capacity = capacity;
        lock = new ReentrantLock();
        fullCondition = lock.newCondition();
        emptyCondition = lock.newCondition();
    }

    /**
     * 生产
     * @param no
     */
    public void produce(int no) {
        lock.lock();
        try {
            int left = no;
            while (left > 0) {
                while (size >= capacity) {
                    log.info("{} before await", Thread.currentThread());
                    fullCondition.await();
                    log.info("{} after await", Thread.currentThread());
                }
                int inc = (left + size) > capacity ? (capacity - size) : left;
                left -= inc;
                size += inc;
                log.info("produce = {}, size = {}", inc, size);
                emptyCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费
     * @param no
     */
    public void consume(int no) {
        lock.lock();
        try {
            int left = no;
            while (left > 0) {
                while (size <= 0) {
                    log.info("{} before await", Thread.currentThread());
                    emptyCondition.await();
                    log.info("{} after await", Thread.currentThread());
                }
                int dec = (size - left) > 0 ? left : size;
                size -= dec;
                left -= dec;
                log.info("consume = {}, size = {}", dec, size);
                fullCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
