package com.liuty.maven.multithread;

/**
 * synchronized：原子性内置锁，排他锁，会导致上下文切换，阻塞操作需要用户态切换到内核态，比较耗时
 * 解决：原子性、可见性、指令重排序
 * 可见性：synchronized块内的共享变量会清空工作内存内容，使用的时候从主内存获取值，
 * 修改的值会同步到主内存。
 * 原子性：一系列操作要不全部执行，要不全部不执行
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
