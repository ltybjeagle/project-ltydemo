package com.liuty.maven.multithread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * @描述: 抽象同步队列，
 * AbstractQueuedSynchronizer: FIFO双向队列，并发锁底层实现逻辑基础
 *      1、阻塞队列，获取锁失败后存入阻塞队列，两个节点信息指向头节点和为节点，进入阻塞队列的线程使用
 *      LockSupport.park()方法阻塞，释放锁的时候使用LockSupport.unpark(线程)方法激活队列里的一个线程
 *      2、NODE对象，队列里实际存储的对象，内部包含阻塞的线程对象、阻塞类型、前后节点地址
 *      3、内部类ConditionObject(条件对象)，内部含有条件队列，不满足条件的线程被保存到条件队列里
 *      4、state状态字段，int类型，使用CAS方式更新此状态字段值，来控制是否获取锁，
 *      控制方式有：独占方式、共享方式
 *      5、可重入锁实现方式就是在state的值+1
 *
 * ReentrantLock：基于AQS实现的可重入独占锁
 *      1、支持公平锁、非公平锁，默认是非公平锁，非公平锁性能要由于公平锁，
 *      2、获取锁更新AQS的state字段为1，重入锁时，在此基础上+1
 *      3、释放锁更新AQS的state字段，每次更新-1，在重入锁的基础上，直到state字段为0，才释放锁
 *      4、非公平锁体现在，释放锁的时候，可能正好有一个线程来申请锁资源，这时系统不去判断阻塞队列里是否
 *      有线程，直接将锁之前分配给新来的线程，省去了线程挂起的流程（挂起耗时，需要切换系统态）。
 *      5、公平锁体现在，释放锁的时候，不管是否有新线程到来，都将从阻塞队列里获取线程分配，将新线程放到
 *      队列里。
 *
 * ReentrantReadWriteLock：读写锁，读写分离，共享读，排他写
 *      1、state状态位字段，int类型，高16位表示读状态，即获取读锁的次数，低16位表示写状态，
 *      即写锁的重入次数
 *      2、读读共享、读写互斥、写写互斥，已经获取写锁的线程，可以再次获取读锁，
 *      3、读写都是可重入锁
 *
 * StampedLock：JDK8提供的新读写锁
 *      1、写锁，独占锁
 *      2、悲观读锁，共享读
 *      3、乐观读锁，不适用锁状态位，使用位运算判断是否有线程抢占写锁，操作的是全局变量的副本（快照），
 *      所以计算的结果可能是一个旧值（一致性问题）
 *      4、在一定条件下三种所可以互相转换
 *      5、锁是不可重入锁
 *      6、内部使用一个双向阻塞队列实现锁
 *      7、获取锁后，会返回一个锁版本号，释放锁的时候使用这个版本号释放锁
 *
 * LockSupport：工具类，底层使用Unsafe实现。
 *      1、线程调用LockSupport方法，必须持有许可证，否则将被阻塞
 *      2、调用unpark方法将被阻塞，其他线程使用unpark方法可以使阻塞线程关联许可证，
 *      获取到许可证将激活状态继续执行。
 *      3、其他线程使用中断方法interrupt()也可以激活阻塞线程，因为unpark方法阻塞的线程在中断状态时不会抛
 *      异常，因此需要在在线程实现逻辑里使用循环判断中断状态。
 *
 * @创建人: Sunny
 * @创建时间: 2019/3/23
 */
public class AQSDemo {

    /**
     * 使用自定义独占锁实现生产者-消费者模型
     */
    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static  int queueSize = 10;

    public static void main(String ...args) {
        /*
         * 读写锁
         */
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

        /*
         * ReentrantLock使用同自定义锁类似
         */
        ReentrantLock rlock = new ReentrantLock();

        /*
         * 生产者-消费者模型(同wait、notify)
         * await：阻塞
         * signal：唤醒一个线程
         * signalAll：唤醒所有线程
         */
        Thread producer = new Thread(() -> {
            lock.lock();
            try {
                while (queue.size() == queueSize) {
                    notEmpty.await();
                }
                queue.add("ele");
                notFull.signalAll();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                while (0 == queue.size()) {
                    notFull.await();
                }
                queue.poll();
                notEmpty.signalAll();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        producer.start();
        consumer.start();

        /*
         * LockSupport测试
         */
        Thread threadOne = new Thread(() -> {
            /*
             * 线程执行方法阻塞，等待其他线程使用unPark唤醒
             * park()：一直阻塞，直到被唤醒
             * parkNanos(long)：被阻塞long时间后，自动唤醒
             * park(Object)：一直被阻塞，但是获取阻塞的信息时，会包含比较全的上下文信息（阻塞的类信息）
             * parkNanos(Object, long)：比park(Object)多了超时时间
             */
            LockSupport.park();
            LockSupport.parkNanos(5000);
        });
        threadOne.start();

        Thread threadSecond = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) { }
            /*
             * 使用unPark唤醒threadOne线程继续执行
             */
            LockSupport.unpark(threadOne);
        });
        threadSecond.start();
    }

    /**
     * 使用StampedLock锁控制
     */
    class point {
        private double x, y;
        private final StampedLock sl = new StampedLock();

        void move(double deltaX, double deltaY) {
            /*
             * 写锁控制，排他锁
             */
            long stamp = sl.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                sl.unlockWrite(stamp);
            }
        }

        /*
         * 乐观读锁
         * @return
         */
        double distanceFromOrigin() {
            long stamp = sl.tryOptimisticRead();
            double currentX = x, currentY = y;
            /*
             * 校验是否有其他写锁抢占
             */
            if (!sl.validate(stamp)) {
                /*
                 * 如果有抢占，使用悲观读锁
                 */
                stamp = sl.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    sl.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        /**
         * 悲观读锁，并尝试转换位写锁
         * @param newX
         * @param newY
         */
        void moveIfAtOrigin(double newX, double newY) {
            long stamp = sl.readLock();
            try {
                while (x == 0.0 && y == 0.0) {
                    /*
                     * 尝试升级读锁为写锁，返回值大于0，升级成功
                     */
                    long ws = sl.tryConvertToWriteLock(stamp);
                    if (ws != 0L) {
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        sl.unlockRead(stamp);
                        stamp = sl.writeLock();
                    }
                }
            } finally {
                sl.unlock(stamp);
            }
        }
    }

    /**
     * 使用读写锁封装安全性列表
     */
    static class ReentrantLockList {
        private ArrayList<String> array = new ArrayList<>();
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();

        public void add(String e) {
            writeLock.lock();
            try {
                array.add(e);
            } finally {
                writeLock.unlock();
            }
        }

        public void remove(String e) {
            writeLock.lock();
            try {
                array.remove(e);
            } finally {
                writeLock.unlock();
            }
        }

        public void get(int index) {
            readLock.lock();
            try {
                array.get(index);
            } finally {
                readLock.unlock();
            }
        }
    }

}

/**
 * 自定义不可重入独占同步器
 */
class NonReentrantLock implements Lock, Serializable {

    private final Sync sync = new Sync();

    /**
     * 锁是否已经被占
     * @return
     */
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    /**
     * 获取锁失败，直接进阻塞队列
     */
    @Override
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 可中断
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 获取锁失败，直接返回
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     * 获取锁失败，阻塞time时间后返回
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    /**
     * 内部帮助类，扩展AbstractQueuedSynchronizer，实现独占锁逻辑
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 锁是否已经被持有
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 如果state为0，尝试获取锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 尝试获取锁, 设置state为0
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 提供条件变量接口
         * @return
         */
        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
