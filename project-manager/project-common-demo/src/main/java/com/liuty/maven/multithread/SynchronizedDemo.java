package com.liuty.maven.multithread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized：原子性内置锁，排他锁，会导致上下文切换，阻塞操作需要用户态切换到内核态，比较耗时
 * 解决问题：
 *      原子性：一系列操作要不全部执行，要不全部不执行
 *      可见性：synchronized块内的共享变量会清空工作内存内容，使用的时候从主内存获取值，
 *      修改的值会同步到主内存。
 *      指令重排序：
 *          1、编译器和处理器会对指令重新排序以便提高运行性能
 *          2、排序前后不影响单线程的执行结果
 *          3、多线程环境会有一致性问题
 */
public class SynchronizedDemo {
    public static void main(String ...args) {
        /*
         * ++value操作位分三步：1、获取值；2、+1计算；3、赋值
         * 此操作不能使用volatile关键字，因为不能保证三部操作的原子性
         * 使用synchronized共享变量的原子性问题，性能不好
         * 可以使用CAS操作替换（原子类替换）
         */
        ThreadSafeCount tsc = new ThreadSafeCount();
        Thread threadA = new Thread(() -> {
            tsc.inc();
            tsc.get();
        });
        Thread threadB = new Thread(() -> {
            tsc.inc();
            tsc.get();
        });

        threadA.start();
        threadB.start();

        /*
         * 锁概念：
         *  阻塞操作：JAVA线程跟系统线程一一对应，阻塞需要用户态切换到系统态，进行线程挂起操作，性能低
         *  悲观所：独占锁，没有获取锁的线程被阻塞
         *  乐观锁：不加锁，通过版本号等方式在更新操作是判断是否更新成功，失败的操作可以重试
         *
         *  公平锁：按照锁的请求顺序获取锁，性能差
         *  非公平锁：获取顺序随机，可能最新的线程最先获得锁（新来的线程还没有阻塞，直接获取锁，
         *  减少用户态系统态切换，是一种性能优化），性能好，默认方式
         *
         *  独占锁：只允许一个线程获取
         *  共享锁：可以允许多个线程获取，如读写锁，多线程读，单线程写
         *
         *  可重入锁：一个线程可以重复进入此线程获取的锁方法
         *
         *  自旋锁：线程在获取锁的时候，不会阻塞，而是尝试多次获取
         */
        // 公平锁
        ReentrantLock pairLock = new ReentrantLock(true);
        try {
            pairLock.lock();
        } finally {
            pairLock.unlock();
        }
        // 非公平锁
        ReentrantLock noPairLock = new ReentrantLock(false);
        try {
            noPairLock.lock();
        } finally {
            noPairLock.unlock();
        }
    }

    public static class ThreadSafeCount {

        private Long value = 0l;

        public synchronized Long get() {
            return value;
        }

        public synchronized void inc() {
            ++value;
        }
    }
}
