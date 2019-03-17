package com.liuty.maven.multithread;

/**
 * volatile：解决共享变量的可见性，但是不保证原子性
 * 处理方式：总线锁、缓存一致性协议
 */
public class VolatileDemo {

    public static void main(String ...args) {
        /*
         * 使用volatile解决多线程中共享变量的可见性问题
         */
        ThreadSafeInteger tsi = new ThreadSafeInteger();
        Thread threadA = new Thread(() -> {
            while (tsi.get() == 0) {

            }
        });
        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(1000);
                tsi.set(1);
            } catch (Exception ex) {

            }
        });

        threadA.start();
        threadB.start();
    }

    public static class ThreadSafeInteger {
        private volatile int value = 0;

        public int get() {
            return value;
        }

        public void set(int value) {
            this.value = value;
        }
    }
}
