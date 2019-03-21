package com.liuty.maven.multithread;

/**
 * 线程池逻辑：
 * ThreadPoolExecutor线程池参数：
 *      corePoolSize：核心线程数量(初始化时可以一次性生成核心数个线程，也可以等待任务逐个生成线程到核心数)
 *      maximumPoolSize：最大线程数量
 *      keepAliveTime：当前线程数 > corePoolSize，多出来的线程会在keepAliveTime之后释放
 *      unit：keepAliveTime的时间单位，比如分钟、小时等
 *      workQueue：队列
 *      threadFactory：需要创建新线程放入线程池的时候，就通过这个线程工厂来创建
 *      handler：当线程、队列都满了之后采取的策略，比如抛出异常等策略
 *
 * 线程池实现：
 * 线程池工具类Executors创建线程池
 *      1、FixedThreadExecutor：易引发任务堆积，内存溢出
 *          定长线程池，corePoolSize == maximumPoolSize
 *          无界阻塞队列
 *      2、CachedThreadExecutor：易引发线程堆积，内存溢出
 *          maximumPoolSize趋于无限大
 *          有限阻塞队列
 * ThreadPoolExecutor类实现自定义线程池
 *
 * 线程池关闭：
 * shutdown：平缓关闭线程任务
 *      1、停止接收新任务
 *      2、等待正在执行的任务完成
 * shutdownNow：强制关闭线程任务
 *      1、关闭正在执行的任务和待执行的任务
 */
public class ThreadPoolExecutorDemo {

    public static void main(String ...args) {

    }
}
