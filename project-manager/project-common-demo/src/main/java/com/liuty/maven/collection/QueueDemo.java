package com.liuty.maven.collection;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @描述: 队列类集合(阻塞队列、非阻塞队列)
 * ConcurrentLinkedQueue：线程安全无界非阻塞链表队列（单向无界链表）
 *      1、存储结构是单项链表
 *      2、使用CAS实现线程安全
 *      3、列表元素使用Node类型封装
 *          Node节点：
 *          item使用volatile修饰,存储节点值
 *          next指向下一个节点
 *      4、队首、队尾两个节点属性，默认指向NULL的哨兵节点，使用volatile修饰
 * 函数：
 *      offer：在队尾添加元素，因是无界队列，所以一直返回true，非阻塞
 *      add：在队尾添加元素，内部使用offer
 *      poll：在队头获取一个元素，并移除元素（通过重设头节点元素移除，移除的节点会在垃圾回收时回收掉）
 *      ，队列空返回NULL
 *      peek：在队头获取一个元素，不移除元素，队列空返回NULL，第一次调用方法时，会删除哨兵节点
 *      ，让队首节点指向队列里的第一个节点或NULL
 *      size：计算队列的元素个数，因使用CAS，没有加锁，所以元素个数可能不准
 *      remove：删除队列的元素，如果存在多个删除第一个
 *      contains：判断队列里是否存在元素，判断不精确
 *
 * LinkedBlockingQueue：独占锁阻塞链表队列（有界队列）
 *      1、存储结构是单项链表
 *      2、队首、队尾两个节点属性，一个记录元素个数的count变量，两个独占锁，一个控制入队，一个控制出队
 *      ，两个锁都提供了条件变量，控制线程不满足条件的时候阻塞到对应条件队列里
 *      3、有界阻塞队列
 *      4、出、入队列需要获取独占锁
 * 函数：
 *      offer：在队尾添加元素，有界队列，有空闲插入成功，如果队列满了丢失元素返回false，非阻塞
 *      put：在队尾添加元素，有空闲插入成功，如果队列满了阻塞当前线程，直到队列有空闲，阻塞，可中断
 *      poll：在队头获取一个元素，并移除元素，队列空返回NULL，非阻塞
 *      peek：在队头获取一个元素，不移除元素，队列空返回NULL，非阻塞
 *      take：在队头获取一个元素，并移除元素，队列空阻塞当前线程，阻塞，可中断
 *      size：计算队列的元素个数，比较准确
 *      remove：删除队列的元素，需要获取双重锁（入队、出队独占锁）
 *
 * ArrayBlockingQueue：独占锁阻塞数组队列（有界队列）
 *      1、存储结构是数组
 *      2、两个下标变量表示入队、出队元素，一个记录元素个数的count变量，一个独占锁，控制入队、出队
 *      ，独占锁提供了两个条件变量，控制线程不满足条件的时候阻塞到对应条件队列里
 *      3、有界阻塞队列
 * 函数：
 *      offer：在队尾添加元素，有界队列，有空闲插入成功，如果队列满了丢失元素返回false，非阻塞
 *      put：在队尾添加元素，有空闲插入成功，如果队列满了阻塞当前线程，直到队列有空闲，阻塞，可中断
 *      poll：在队头获取一个元素，并移除元素，队列空返回NULL，非阻塞
 *      take：在队头获取一个元素，并移除元素，队列空阻塞当前线程，阻塞，可中断
 *      peek：在队头获取一个元素，不移除元素，队列空返回NULL，非阻塞
 *      size：计算队列的元素个数，准确
 *
 * PriorityBlockingQueue：有优先级的无界阻塞队列（无界队列）
 *      1、存储结构是平衡二叉堆，底层数组
 *      2、每次出队都是队列优先级最高或最低的元素
 *      3、有默认初始容量，默认11，可以自动扩容
 *      4、一个自旋锁（值集：0 没有线程、1 已经有线程），使用CAS操作更新自旋锁的值
 *      ，只有获取了自旋锁才能进行扩容操作，
 *          扩容操作：
 *          原容量 < 64 ，扩容：原容量 + (原容量 + 2)
 *          原容量 >= 64，扩容：原容量 + (原容量 >> 1)
 *      5、因为要实现优先级，所以需要提供比较器（comparator），默认比较器NULL，使用元素自身的比较方法
 * 函数：因内部使用平衡二叉堆，增、删操作后需要做堆调整，以满足平衡二叉堆条件
 *      offer：在队列里添加元素，无界队列，一直返回true，非阻塞
 *      poll：获取队列内部堆的根节点元素，并移除元素，队列空返回NULL，非阻塞
 *      put：在队列里添加元素，内部使用offer，非阻塞
 *      take：获取队列内部堆的根节点元素，并移除元素，队列空阻塞当前线程，阻塞，可中断
 *      size：计算队列的元素个数，准确
 *
 * DelayQueue：并发无界阻塞延迟队列
 *      1、内部使用优先级队列存放数据
 *      2、对内的元素需要实现Delayed接口,每个元素都还有过期时间，只有过期的元素才能出队
 * 函数：
 *      offer：在队列里添加元素，无界队列，一直返回true，非阻塞
 *      take：获取队列延迟过期的元素，没有过期元素时等待，阻塞，可中断
 *      poll：获取队头过期的元素，并移除元素，没有过期元素返回NULL，非阻塞
 *      size：计算队列的元素个数，包含过期和没过期的元素，准确
 *
 * @创建人: Sunny
 * @创建时间: 2019/3/30
 */
public class QueueDemo {

    public static void main(String ...args) {
        /*
         * 线程安全无界非阻塞链表队列（无界队列）
         */
        new ConcurrentLinkedQueue();
        /*
         * 独占锁阻塞链表队列（有界队列）
         */
        new LinkedBlockingQueue();
        /*
         * 独占锁阻塞数组队列（有界队列）
         */
        new ArrayBlockingQueue(5);
        /*
         * 有优先级的无界阻塞队列（无界队列）
         */
        PriorityBlockingQueue<Task> priorityBlockingQueue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setPriority(random.nextInt(10));
            task.setTaskName("taskName" + i);
            priorityBlockingQueue.offer(task);
        }
        while (!priorityBlockingQueue.isEmpty()) {
            Task task = priorityBlockingQueue.poll();
            if (null != task) {
                System.out.println(task.toString());
            }
        }
        /*
         * 并发无界阻塞延迟队列
         */
        DelayQueue<DelayedEle> delayQueue = new DelayQueue<>();
        for (int i = 0; i < 10; i++) {
            DelayedEle element = new DelayedEle(random.nextInt(500)
                    , "taskName" + i);
            delayQueue.offer(element);
        }
        DelayedEle ele = null;
        try {
            for (;;) {
                while ((ele = delayQueue.take()) != null) {
                    System.out.println(ele.toString());
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    static class DelayedEle implements Delayed {
        private final long delayTime; // 延期时间
        private final long expire; // 到期时间
        private String taskName;

        public DelayedEle(long delay, String taskName) {
            this.delayTime = delay;
            this.taskName = taskName;
            this.expire = System.currentTimeMillis() + delay;
        }

        /**
         * 剩余时间
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis()
                    , TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "DelayedEle{" +
                    "delayTime=" + delayTime +
                    ", expire=" + expire +
                    ", taskName='" + taskName + '\'' +
                    '}';
        }
    }

    /**
     * 优先级的无界阻塞队列测试类
     */
    static class Task implements Comparable<Task> {
        private int priority = 0;
        private String taskName;

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "priority=" + priority +
                    ", tsakName='" + taskName + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Task o) {
            if (this.priority >= o.priority) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
